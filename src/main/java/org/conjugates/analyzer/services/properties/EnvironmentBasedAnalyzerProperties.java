package org.conjugates.analyzer.services.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentBasedAnalyzerProperties implements AnalyzerProperties {

  private static final String NAME_PROPERTY_KEY = "name";

  private final Environment environment;

  @Autowired
  public EnvironmentBasedAnalyzerProperties(Environment environment) {
    this.environment = environment;
  }

  @Override
  public String getName() {
    return environment.getProperty(NAME_PROPERTY_KEY);
  }

}
