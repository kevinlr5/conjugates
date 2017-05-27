variable "aws_access_key_deployment" {
  description = "The AWS access key for deployment."
}

variable "aws_secret_key_deployment" {
  description = "The AWS secret key for deployment."
}

variable "aws_cert_arn" {
  description = "The website certificate ARN"
}

variable "region" {
  description = "The AWS region to create resources in."
  default = "us-west-2"
}

variable "ecs_cluster_name" {
  description = "The name of the Amazon ECS cluster."
  default = "sentiment"
}

variable "docker_username" {
  description = "The name of the dockerhub account."
}

variable "version" {
  description = "The version of the docker image to use when provisioning the analyzer."
}

variable "deploy_id" {
  description = "The unique identifier of the deployment."
}

variable "deploy_type" {
  description = "The type of deployment. Non 'test' deployments are persistent core deployments"
}

/* ECS optimized AMIs per region */
variable "amis" {
  default = {
    us-west-2 = "ami-62d35c02"
  }
}

variable "instance_type" {
  default = "t2.small"
}

variable "api_address" {
  description = "The api of the analyzer"
  default = "none"
}

variable "vpc_id" {
  description = "The id of the pre-created vpc"
}

variable "db_hostname" {
  description = "The hostname of the db"
}

variable "db_user" {
  description = "The username for the db"
}

variable "db_password" {
  description = "The password for the db"
}

variable "db_schema" {
  description = "The schema in the db"
}