package org.sentiment.analyzer.services.documents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity {

  private final String value;
  private final String rawValue;
  private final String type;

  @JsonCreator
  public Entity(
      @JsonProperty("value") String value,
      @JsonProperty("rawValue") String rawValue,
      @JsonProperty("type") String type) {
    this.value = value;
    this.rawValue = rawValue;
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public String getRawValue() {
    return rawValue;
  }

  public String getType() {
    return type;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((rawValue == null) ? 0 : rawValue.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    Entity other = (Entity) obj;
    if (rawValue == null) {
      if (other.rawValue != null) {
        return false;
      }
    } else if (!rawValue.equals(other.rawValue)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Entity [value=" + value + ", rawValue=" + rawValue + ", type=" + type + "]";
  }

}
