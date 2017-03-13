variable "aws_access_key_root" {
  description = "The AWS access key for the root account."
}

variable "aws_secret_key_root" {
  description = "The AWS secret key for the root account."
}

variable "region" {
  description = "The AWS region to create resources in."
  default = "us-west-2"
}