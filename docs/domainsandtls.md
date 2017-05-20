# Domains and TLS

This document gives an overview of how to setup a domains and SSL certs so that the application can be accessed conveniently and securely.

## Domain Registration

Go to the AWS domain registry in Route53 and register your domain. After the domain exists, you can let the deployment scripts take over.

## DNS Entries

The domain entries will be handled by scripts and are as follows:
- Name: yourwebsite.com; Type: A; Value: ALIAS address.of.fe.elb
- Name: api.yourwebsite.com; Type: A; Value: ALIAS address.of.analyzer.elb
- Name: www.yourwebsite.com; Type: CNAME; Value: yourwebsite.com

## TLS Certificate Creation

Go to the AWS Certificate Manager and create a new certificate for your domain.
- Domain: www.yourwebsite.com
- Alternate: yourwebsite.com
- Alternate: api.yourwebsite.com

## TLS in the Application

TLS is terminated at the ELB. Everything below the level of the ELB does not use TLS. The certificate is deployed in both the frontend and analyzer ELBs, but they redirect to http addresses internally.