## Provider

provider "aws" {
  access_key = "${var.aws_access_key}"
  secret_key = "${var.aws_secret_key}"
  region     = "${var.region}"
}

## Network

data "aws_availability_zones" "available" {}

resource "aws_vpc" "main" {
  cidr_block = "10.10.0.0/16"
}

resource "aws_subnet" "main" {
  count             = "${var.az_count}"
  cidr_block        = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index)}"
  availability_zone = "${data.aws_availability_zones.available.names[count.index]}"
  vpc_id            = "${aws_vpc.main.id}"
}

resource "aws_internet_gateway" "gw" {
  vpc_id = "${aws_vpc.main.id}"
}

resource "aws_route_table" "r" {
  vpc_id = "${aws_vpc.main.id}"

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.gw.id}"
  }
}

resource "aws_route_table_association" "a" {
  count          = "${var.az_count}"
  subnet_id      = "${element(aws_subnet.main.*.id, count.index)}"
  route_table_id = "${aws_route_table.r.id}"
}

## IAM

resource "aws_iam_role" "ecs_service" {
  name = "analyzer_ecs_role"

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
  name = "analyzer_ecs_policy"
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
  name  = "analyzer-ecs-instprofile"
  roles = ["${aws_iam_role.app_instance.name}"]
}

resource "aws_iam_role" "app_instance" {
  name = "analyzer-ecs--instance-role"

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
  name   = "AnalyzerEcsInstanceRole"
  role   = "${aws_iam_role.app_instance.name}"
  policy = "${data.template_file.instance_profile.rendered}"
}

## Security Groups

resource "aws_security_group" "elb_sg" {
  description = "controls access to the application ELB"
  name   = "ecs-elb-sg"
  vpc_id = "${aws_vpc.main.id}"

  ingress {
    protocol    = "tcp"
    from_port   = 8080
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
}

resource "aws_security_group" "instance_sg" {
  description = "controls direct access to application instances"
  name        = "ecs-instance-sg"
  vpc_id      = "${aws_vpc.main.id}"

  ingress {
    protocol  = "tcp"
    from_port = 8080
    to_port   = 8080

    security_groups = [
      "${aws_security_group.elb_sg.id}",
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

## EC2

resource "aws_launch_configuration" "app" {
  security_groups = [
    "${aws_security_group.instance_sg.id}",
  ]

  image_id                    = "${lookup(var.amis, var.region)}"
  instance_type               = "${var.instance_type}"
  iam_instance_profile        = "${aws_iam_instance_profile.app.name}"
  associate_public_ip_address = true

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_autoscaling_group" "app" {
  name                 = "ecs-asg"
  vpc_zone_identifier  = ["${aws_subnet.main.*.id}"]
  min_size             = 1
  max_size             = 2
  desired_capacity     = 1
  launch_configuration = "${aws_launch_configuration.app.name}"
}

resource "aws_elb" "analyzer-elb" {
  name               = "analyzer-elb"
  subnets            = ["${aws_subnet.main.*.id}"]
  security_groups    = [
    "${aws_security_group.elb_sg.id}",
  ]

  listener {
    instance_port     = 8080
    instance_protocol = "http"
    lb_port           = 8080
    lb_protocol       = "http"
  }

  health_check {
    healthy_threshold   = 2
    unhealthy_threshold = 10
    timeout             = 3
    target              = "HTTP:8080/api/info/"
    interval            = 5
  }

  connection_draining = false
}

data "template_file" "analyzer_task_definition" {
  template = "${file("task_definitions/analyzer.json")}"

  vars {
    analyzer_docker_version = "${var.analyzer_docker_version}"
  }
}

resource "aws_ecs_task_definition" "analyzer" {
  family                = "analyzer"
  container_definitions = "${data.template_file.analyzer_task_definition.rendered}"
}

resource "aws_ecs_service" "analyzer" {
  name            = "analyzer"
  cluster         = "${aws_ecs_cluster.conjugates.id}"
  task_definition = "${aws_ecs_task_definition.analyzer.arn}"
  desired_count   = 1
  iam_role        = "${aws_iam_role.ecs_service.name}"

  load_balancer {
    elb_name       = "${aws_elb.analyzer-elb.id}"
    container_name = "analyzer"
    container_port = 8080
  }

  depends_on = [
    "aws_iam_role_policy.ecs_service",
    "aws_elb.analyzer-elb"
  ]
}

resource "aws_ecs_cluster" "conjugates" {
  name = "${var.ecs_cluster_name}"
}
