package org.conjugates.analyzer;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class AnalyzerApplication {

  /**
   * Runs the application.
   */
  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(AnalyzerApplication.class);
    application.setBannerMode(Mode.OFF);
    application.run(args);
  }
}
