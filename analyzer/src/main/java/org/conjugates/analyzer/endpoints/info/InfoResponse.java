package org.conjugates.analyzer.endpoints.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoResponse {

  private final String name;
  private final String version;
  private final String commitHash;

  @JsonCreator
  public InfoResponse(@JsonProperty("name") String name, @JsonProperty("version") String version,
      @JsonProperty("commitHash") String commitHash) {
    this.name = name;
    this.version = version;
    this.commitHash = commitHash;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public String getCommitHash() {
    return commitHash;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((commitHash == null) ? 0 : commitHash.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((version == null) ? 0 : version.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    InfoResponse other = (InfoResponse) obj;
    if (commitHash == null) {
      if (other.commitHash != null) {
        return false;
      }
    } else if (!commitHash.equals(other.commitHash)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (version == null) {
      if (other.version != null) {
        return false;
      }
    } else if (!version.equals(other.version)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "InfoResponse [name=" + name + ", version=" + version + ", commitHash=" + commitHash
        + "]";
  }

}
