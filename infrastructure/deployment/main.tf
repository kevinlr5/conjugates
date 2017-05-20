## Provider

provider "aws" {
  access_key = "${var.aws_access_key_deployment}"
  secret_key = "${var.aws_secret_key_deployment}"
  region     = "${var.region}"
}

## Network

data "aws_availability_zones" "available" {}

resource "aws_vpc" "main" {
  cidr_block = "10.10.0.0/16"

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

resource "aws_subnet" "main" {
  count             = "${var.az_count}"
  cidr_block        = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index)}"
  availability_zone = "${data.aws_availability_zones.available.names[count.index]}"
  vpc_id            = "${aws_vpc.main.id}"

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

resource "aws_internet_gateway" "gw" {
  vpc_id = "${aws_vpc.main.id}"

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

resource "aws_route_table" "r" {
  vpc_id = "${aws_vpc.main.id}"

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.gw.id}"
  }

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

resource "aws_route_table_association" "a" {
  count          = "${var.az_count}"
  subnet_id      = "${element(aws_subnet.main.*.id, count.index)}"
  route_table_id = "${aws_route_table.r.id}"
}

## IAM

resource "aws_iam_role" "ecs_service" {
  name = "analyzer-ecs-role-${var.deploy_id}"

  assume_role_policy = <<EOF
{
  "Version": "2008-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "ecs.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "ecs_service" {
  name = "analyzer-ecs-policy-${var.deploy_id}"
  role = "${aws_iam_role.ecs_service.name}"

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ec2:Describe*",
        "elasticloadbalancing:DeregisterInstancesFromLoadBalancer",
        "elasticloadbalancing:DeregisterTargets",
        "elasticloadbalancing:Describe*",
        "elasticloadbalancing:RegisterInstancesWithLoadBalancer",
        "elasticloadbalancing:RegisterTargets"
      ],
      "Resource": "*"
    }
  ]
}
EOF
}

resource "aws_iam_instance_profile" "app" {
  name  = "analyzer-ecs-instance-profile-${var.deploy_id}"
  roles = ["${aws_iam_role.app_instance.name}"]
}

resource "aws_iam_role" "app_instance" {
  name = "analyzer-ecs-instance-role-${var.deploy_id}"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "ec2.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

data "template_file" "instance_profile" {
  template = "${file("policies/instance-profile-policy.json")}"
}

resource "aws_iam_role_policy" "instance" {
  name   = "AnalyzerEcsInstanceRole-${var.deploy_id}"
  role   = "${aws_iam_role.app_instance.name}"
  policy = "${data.template_file.instance_profile.rendered}"
}

## Security Groups

resource "aws_security_group" "analyzer_elb_sg" {
  description = "controls access to the application ELB"
  name   = "ecs-elb-analyzer-sg-${var.deploy_id}"
  vpc_id = "${aws_vpc.main.id}"

  ingress {
    protocol    = "tcp"
    from_port   = 8443
    to_port     = 9090
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"

    cidr_blocks = [
      "0.0.0.0/0",
    ]
  }

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

resource "aws_security_group" "frontend_elb_sg" {
  description = "controls access to the application ELB"
  name   = "ecs-elb-frontend-sg-${var.deploy_id}"
  vpc_id = "${aws_vpc.main.id}"

  ingress {
    protocol    = "tcp"
    from_port   = 443
    to_port     = 8080
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    protocol    = "tcp"
    from_port   = 80
    to_port     = 8080
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"

    cidr_blocks = [
      "0.0.0.0/0",
    ]
  }

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

resource "aws_security_group" "instance_sg" {
  description = "controls direct access to application instances"
  name        = "ecs-instance-sg-${var.deploy_id}"
  vpc_id      = "${aws_vpc.main.id}"

  ingress {
    protocol  = "tcp"
    from_port = 9090
    to_port   = 9090

    security_groups = [
      "${aws_security_group.analyzer_elb_sg.id}",
    ]
  }

  ingress {
    protocol  = "tcp"
    from_port = 8080
    to_port   = 8080

    security_groups = [
      "${aws_security_group.frontend_elb_sg.id}",
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

## EC2

data "template_file" "user_data" {
  template = "${file("${path.module}/user_data.sh")}"

  vars {
    ecs_cluster_name = "${aws_ecs_cluster.conjugates.name}"
  }
}

resource "aws_launch_configuration" "app" {
  security_groups = [
    "${aws_security_group.instance_sg.id}",
  ]

  name                        = "conjugates-${var.deploy_id}"
  image_id                    = "${lookup(var.amis, var.region)}"
  instance_type               = "${var.instance_type}"
  iam_instance_profile        = "${aws_iam_instance_profile.app.name}"
  associate_public_ip_address = true
  user_data                   = "${data.template_file.user_data.rendered}"

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_autoscaling_group" "analyzer_app" {
  name                 = "ecs-asg-${var.deploy_id}"
  vpc_zone_identifier  = ["${aws_subnet.main.*.id}"]
  min_size             = 1
  max_size             = 2
  desired_capacity     = 2
  launch_configuration = "${aws_launch_configuration.app.name}"

  tag {
    key                 = "deploy_id" 
    value               = "${var.deploy_id}"
    propagate_at_launch = true
  }
  tag {
    key                 = "deploy_type"
    value               = "${var.deploy_type}"
    propagate_at_launch = true
  }
  tag {
    key                 = "version"
    value               = "${var.version}"
    propagate_at_launch = true
  }
}

resource "aws_elb" "analyzer-elb" {
  name               = "analyzer-elb-${var.deploy_id}"
  subnets            = ["${aws_subnet.main.*.id}"]
  security_groups    = [
    "${aws_security_group.analyzer_elb_sg.id}",
  ]

  listener {
    instance_port     = 9090
    instance_protocol = "http"
    lb_port           = 8443
    lb_protocol       = "https"
    ssl_certificate_id = "${var.aws_cert_arn}"
  }

  health_check {
    healthy_threshold   = 2
    unhealthy_threshold = 10
    timeout             = 3
    target              = "HTTP:9090/api/info"
    interval            = 5
  }

  connection_draining = false

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

resource "aws_elb" "frontend-elb" {
  name               = "frontend-elb-${var.deploy_id}"
  subnets            = ["${aws_subnet.main.*.id}"]
  security_groups    = [
    "${aws_security_group.frontend_elb_sg.id}",
  ]

  listener {
    instance_port     = 8080
    instance_protocol = "http"
    lb_port           = 443
    lb_protocol       = "https"
    ssl_certificate_id = "${var.aws_cert_arn}"
  }

  listener {
    instance_port     = 8080
    instance_protocol = "http"
    lb_port           = 80
    lb_protocol       = "http"
  }

  health_check {
    healthy_threshold   = 2
    unhealthy_threshold = 10
    timeout             = 3
    target              = "HTTP:8080/"
    interval            = 5
  }

  connection_draining = false

  tags {
    deploy_id   = "${var.deploy_id}"
    deploy_type = "${var.deploy_type}"
    version     = "${var.version}"
  }
}

data "template_file" "analyzer_task_definition" {
  template = "${file("task_definitions/analyzer.json")}"

  vars {
    docker_username = "${var.docker_username}"
    version = "${var.version}"
    deploy_id = "${var.deploy_id}"
  }
}

data "template_file" "frontend_task_definition" {
  template = "${file("task_definitions/frontend.json")}"

  vars {
    docker_username = "${var.docker_username}"
    version = "${var.version}"
    deploy_id = "${var.deploy_id}"
    analyzer_api_url = "${var.deploy_type == "test" ? aws_elb.analyzer-elb.dns_name : var.develop_api}"
  }

  depends_on = [
    "aws_elb.analyzer-elb"
  ]
}

resource "aws_ecs_task_definition" "analyzer" {
  family                = "analyzer-${var.deploy_id}"
  container_definitions = "${data.template_file.analyzer_task_definition.rendered}"
}

resource "aws_ecs_task_definition" "frontend" {
  family                = "frontend-${var.deploy_id}"
  container_definitions = "${data.template_file.frontend_task_definition.rendered}"
}

resource "aws_ecs_service" "analyzer" {
  name            = "analyzer-${var.deploy_id}"
  cluster         = "${aws_ecs_cluster.conjugates.id}"
  task_definition = "${aws_ecs_task_definition.analyzer.arn}"
  desired_count   = 1
  iam_role        = "${aws_iam_role.ecs_service.name}"

  load_balancer {
    elb_name       = "${aws_elb.analyzer-elb.id}"
    container_name = "analyzer-${var.deploy_id}"
    container_port = 9090
  }

  depends_on = [
    "aws_iam_role_policy.ecs_service",
    "aws_elb.analyzer-elb"
  ]
}

resource "aws_ecs_service" "frontend" {
  name            = "frontend-${var.deploy_id}"
  cluster         = "${aws_ecs_cluster.conjugates.id}"
  task_definition = "${aws_ecs_task_definition.frontend.arn}"
  desired_count   = 1
  iam_role        = "${aws_iam_role.ecs_service.name}"

  load_balancer {
    elb_name       = "${aws_elb.frontend-elb.id}"
    container_name = "frontend-${var.deploy_id}"
    container_port = 8080
  }

  depends_on = [
    "aws_iam_role_policy.ecs_service",
    "aws_elb.frontend-elb"
  ]
}

resource "aws_ecs_cluster" "conjugates" {
  name = "${var.ecs_cluster_name}-${var.deploy_id}"
}

output "analyzer_dns_name" {
  value = "${aws_elb.analyzer-elb.dns_name}"
}

output "frontend_dns_name" {
  value = "${aws_elb.frontend-elb.dns_name}"
}

output "analyzer_hosted_zone" {
  value = "${aws_elb.analyzer-elb.zone_id}"
}

output "frontend_hosted_zone" {
  value = "${aws_elb.frontend-elb.zone_id}"
}