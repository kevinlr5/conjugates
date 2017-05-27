package org.sentiment.analyzer.services.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentBasedAnalyzerProperties implements AnalyzerProperties {

  private static final String NAME_PROPERTY_KEY = "name";
  private static final String VERSION_PROPERTY_KEY = "version";
  private static final String COMMIT_HASH_PROPERTY_KEY = "commit";
  private static final String PORT_PROPERTY_KEY = "server.port";
  private static final String DB_HOSTNAME_KEY = "DB_HOSTNAME";
  private static final String DB_USER_KEY = "DB_USER";
  private static final String DB_PASSWORD_KEY = "DB_PASSWORD";
  private static final String DB_SCHEMA_KEY = "DB_SCHEMA";

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

  @Override
  public String getDbHostname() {
    return environment.getRequiredProperty(DB_HOSTNAME_KEY);
  }

  @Override
  public String getDbUser() {
    return environment.getRequiredProperty(DB_USER_KEY);
  }

  @Override
  public String getDbPassword() {
    return environment.getRequiredProperty(DB_PASSWORD_KEY);
  }

  @Override
  public String getDbSchema() {
    return environment.getRequiredProperty(DB_SCHEMA_KEY);
  }

}
