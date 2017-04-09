package org.conjugates.frontend;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource(value = "classpath:dev.properties")
public class FrontendApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(FrontendApplication.class);
    application.setBannerMode(Mode.OFF);
    application.run(args);
  }

}
