package org.sentiment.frontend;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ResourceTransformer;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource(value = "classpath:dev.properties")
public class FrontendApplication {

  private static final String MAIN_JS_FILE = "main.js";
  private static final String API_URL_PATTERN = "@API_URL";
  private static final String API_URL_KEY = "analyzer.api.url";

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(FrontendApplication.class);
    application.setBannerMode(Mode.OFF);
    application.run(args);
  }

  @Bean
  public WebMvcConfigurer urlTransformer(Environment environment) {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String url = environment.getRequiredProperty(API_URL_KEY);
        ResourceTransformer transformer =
            new RegexSubstitutionResourceTransformer(
                MAIN_JS_FILE, API_URL_PATTERN, url);
        registry.addResourceHandler("/**")
          .addResourceLocations("classpath:/static/")
          .resourceChain(true)
          .addTransformer(transformer);
      }

    };
  }

}
