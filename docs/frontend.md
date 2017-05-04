# Frontend Service

'frontend' is a java server built on Spring and run on Tomcat. However, this server just serves static web content. The web application is a single page application built using a combination of Typescript, React, Sass, and JSX.

# Build

The code is located in the 'frontend-static' project and is built by webpack and node into static content that can be served by any webserver. Additionally, the API docs from the analyzer are built and packaged in the static assets. There is only a single build with the api url getting replaced by the Spring server when the main js file is served. After the assets are built, they're copied into a resource directory in the 'frontend-server' project, which packages them up into a runnable jar. That gets packaged into a Docker container with Java 8 much like the 'analyzer' project.

# Why serve with Tomcat

Since the built assets are static, they could be served by just about anything. The simple way to do this is with S3 and Cloudfront. But, I wanted to experiment some more with ECS, so I decided to Dockerize the build. I already had a working Spring template for doing this, so I went with that. If high usage performance were a concern, I would replace the Java/Tomcat serving method with a more performant webserver like Apache or Nginx.

# The Frontend

The Frontend is built primarily in Typescript and React. It's a simple single page web application that is meant to be highly responsive. In it, users can submit articles to the backend for sentiment analysis and view the results of that analysis.