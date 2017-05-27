# Deployment

This document describes how to deploy the Sentiment Analyzer project to AWS. This has been almost entirely automated, so it's as easy as setting up the CI/CD server! Each PR builds and deploys to AWS to validate that everything works and every merge to `develop` upgrades the persistent `develop` instance. The code that manages all of this can be found in the `infrastructure` project.

## Terraform

The Sentiment Analyzer uses Terraform to setup the AWS account for deployment and to create the deployments. Terraform is a deployment orchestrator. It takes in configuration that describes a set of AWS resources, complete with dynamic configuration. When run, it materializes this state in AWS and saves it to a file. Part of the configuration of the AWS account is the creation of an S3 bucket to store these terraform state files, so deployments can later be destroyed or upgraded.

### Installing Terraform

- https://www.terraform.io/

## Configuring the AWS account

1. Create an AWS account.
2. Get keys for your root account and export them as `AWS_ACCESS_KEY_ROOT` and `AWS_SECRET_KEY_ROOT`.
3. Create a dockerhub account.
4. Export your dockerhub username and password as `DOCKER_USERNAME` and `DOCKER_PASSWORD`.
5. Register your domain name (`yourdomain.com`) in the Route53 default hosted zone
6. Export `yourdomain.com` as `DOMAIN`
7. Create a cert in Certificate Manager for `www.yourdomain.com` with `api.yourdomain.com` and `yourdomain.com` as alternates
8. Install terraform and place it on your path.
9. `./gradlew clean createAwsAccount`
10. In the ouput, you should see a variable called `terraform_bucket_name`. Save its value.
11. Login to the AWS console, generate keys for the `deployer` IAM user, and save the keys locally.

## Structure of the account

The previous task creates an S3 bucket in the account for terraform state files. It even saves the state of AWS resources in the account, which were just created. Each deployment will write state into this bucket. Upon the destruction of a deployment, its state gets deleted. Permanent instances that are continuously upgraded (develop) have files that point to the current deployment.

A deployment user is also created. This user, which is called `deployer`, only has permissions to do the tasks needed to create deployments in the account.

## Deployments of the analyzer

Deployments of the analyzer can be done locally or through a CI/CD server (this repo is preconfigured with CircleCI). Each deployment creates a unique set of resources that is separate from other deployments. Therefore each deployment can be destroyed independently without affecting other deployments, making them easier to test.

## Structure of a deployment

The Sentiment Analyzer is a straightforward containerized application that is deployed using Amazon ECS. An ECS cluster is created with an analyzer and frontend services that each have one exposed port. Internally, there are two ELB instances on top of EC2 instances. The EC2 instances are running ECS optimized images (coreos), which are running the analyzer and frontend docker containers retrieved from dockerhub. All of these EC2 resources are running inside a VPC and do not allow outside access except to the single ports exposed by the ELBs. In order to facilitate proper communication, there are some IAM resources created that are utilized by some of the services.

## Deploying a test instance

1. Install docker
2. Export the value of `terraform_bucket_name` as `TERRAFORM_STATE_BUCKET_NAME`
3. Export the keys for the `deployer` account as `AWS_ACCESS_KEY_DEPLOYMENT` and `AWS_SECRET_KEY_DEPLOYMENT`.
4. Export the ARN of your cert as `AWS_CERT_ARN`
5. Export the DB info as `DB_HOSTNAME` and `DB_USER_PASSWORD`
6. `./gradlew clean build deployTest`
7. The previous command will complete when it verifies that the deployment is working. It writes its state to the S3 bucket.
8. To destroy the test deployment, take the deployId from the log and run `./gradlew clean destroyDeployTest -PdeployId=theId`

## Configuring a CI/CD server (CircleCi)

1. Hook up CircleCi to your github repo
2. Create a trusty Ubuntu build for the repo and disable builds for non-PRs
3. In the build settings, export the `AWS_ACCESS_KEY_DEPLOYMENT`, `AWS_SECRET_KEY_DEPLOYMENT`, `AWS_CERT_ARN`, `DB_HOSTNAME`, `DB_USER_PASSWORD`, `DOCKER_USERNAME`, `DOCKER_PASSWORD`, `DOMAIN`, and `TERRAFORM_STATE_BUCKET_NAME` environment variables

- A full deploy to AWS will occur on each PR build
- The persistent `develop` instance will be upgraded after each merge to develop

## Continuous deployment of develop (with zero downtime upgrades)

The CI/CD build is designed to create a persistent deployment based on the `develop` branch. Each merge to `develop` upgrades this deployment with zero downtime. The strategy employed is a variant of blue/green upgrading. A second full `develop` deployment is created and tested alongside the old one. When it's ready to go, a DNS based switchover occurs. ALIAS records are updated with a 60 second TTL to point at the new deployment and the old deployment is destroyed. Users of the frontend should not see an interruption.

## DNS vs ELB style upgrades

This project currently uses DNS based upgrading, but that form of upgrade is sub-optimal because of client-side DNS caching. It's being utilized because it's simple. An alternate technique allows public DNS queries to resolve to the same address, but switches out the servers underneath. This can be achieved by pointing at a single ELB and rotating the EC2 instances registered to it. There is more information here: https://cloudnative.io/blog/2015/02/the-dos-and-donts-of-bluegreen-deployment/