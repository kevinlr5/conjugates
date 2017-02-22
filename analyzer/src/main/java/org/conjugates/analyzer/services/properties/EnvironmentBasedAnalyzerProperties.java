package org.conjugates.analyzer.services.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentBasedAnalyzerProperties implements AnalyzerProperties {

  private static final String NAME_PROPERTY_KEY = "name";
  private static final String VERSION_PROPERTY_KEY = "app.version";
  private static final String COMMIT_HASH_PROPERTY_KEY = "git.commit";
  private static final String PORT_PROPERTY_KEY = "server.port";

  private final Environment environment;

  @Autowired
  public EnvironmentBasedAnalyzerProperties(Environment environment) {
    this.environment = environment;
  }

  @Override
  public String getName() {
    return environment.getRequiredProperty(NAME_PROPERTY_KEY);
  }

  @Override
  public String getVersion() {
    return environment.getRequiredProperty(VERSION_PROPERTY_KEY);
  }

  @Override
  public String getCommitHash() {
    return environment.getRequiredProperty(COMMIT_HASH_PROPERTY_KEY);
  }

  @Override
  public int getPort() {
    return Integer.parseInt(environment.getRequiredProperty(PORT_PROPERTY_KEY));
  }

}
