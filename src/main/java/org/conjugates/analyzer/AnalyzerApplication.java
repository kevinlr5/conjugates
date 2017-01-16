package org.conjugates.analyzer;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class AnalyzerApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(AnalyzerApplication.class);
    application.setBannerMode(Mode.OFF);
    application.run(args);
  }
}
