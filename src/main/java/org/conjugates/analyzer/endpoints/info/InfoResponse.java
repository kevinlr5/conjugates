package org.conjugates.analyzer.endpoints.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoResponse {

  private final String name;
  private final String version;

  @JsonCreator
  public InfoResponse(@JsonProperty("name") String name, @JsonProperty("version") String version) {
    this.name = name;
    this.version = version;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

}
