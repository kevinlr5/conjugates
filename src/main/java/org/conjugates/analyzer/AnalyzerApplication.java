package org.conjugates.analyzer;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class AnalyzerApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(AnalyzerApplication.class);
    application.setBannerMode(Mode.OFF);
    application.run(args);
  }
}
