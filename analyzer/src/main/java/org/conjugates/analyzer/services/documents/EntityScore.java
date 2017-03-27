package org.conjugates.analyzer.services.documents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityScore {

  private final Entity entity;
  private final int averageScore;
  private final int numberOfMentions;
  private final int weight;

  @JsonCreator
  public EntityScore(
      @JsonProperty("entity") Entity entity,
      @JsonProperty("averageScore") int averageScore,
      @JsonProperty("aggregateScore") int aggregateScore,
      @JsonProperty("numberOfMentions") int numberOfMentions,
      @JsonProperty("weight") int weight) {
    this.entity = entity;
    this.averageScore = averageScore;
    this.numberOfMentions = numberOfMentions;
    this.weight = weight;
  }

  public Entity getEntity() {
    return entity;
  }

  public int getAverageScore() {
    return averageScore;
  }

  public int getNumberOfMentions() {
    return numberOfMentions;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + averageScore;
    result = prime * result + ((entity == null) ? 0 : entity.hashCode());
    result = prime * result + numberOfMentions;
    result = prime * result + weight;
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
    EntityScore other = (EntityScore) obj;
    if (averageScore != other.averageScore) {
      return false;
    }
    if (entity == null) {
      if (other.entity != null) {
        return false;
      }
    } else if (!entity.equals(other.entity)) {
      return false;
    }
    if (numberOfMentions != other.numberOfMentions) {
      return false;
    }
    if (weight != other.weight) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "EntityScore [entity=" + entity + ", averageScore=" + averageScore
        + ", numberOfMentions=" + numberOfMentions + ", weight=" + weight + "]";
  }

}
