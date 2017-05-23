package org.sentiment.analyzer.services.documents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class DocumentScore {

  private final Collection<EntityScore> entityScores;
  private final int averageScore;
  private final int weight;

  @JsonCreator
  public DocumentScore(
      @JsonProperty("entityScores") Collection<EntityScore> entityScores,
      @JsonProperty("averageScore") int averageScore,
      @JsonProperty("weight") int weight) {
    this.entityScores = entityScores;
    this.averageScore = averageScore;
    this.weight = weight;
  }

  public Collection<EntityScore> getEntityScores() {
    return entityScores;
  }

  public int getAverageScore() {
    return averageScore;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + averageScore;
    result = prime * result + ((entityScores == null) ? 0 : entityScores.hashCode());
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
    DocumentScore other = (DocumentScore) obj;
    if (averageScore != other.averageScore) {
      return false;
    }
    if (entityScores == null) {
      if (other.entityScores != null) {
        return false;
      }
    } else if (!entityScores.equals(other.entityScores)) {
      return false;
    }
    if (weight != other.weight) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "DocumentScore [entityScores=" + entityScores + ", averageScore=" + averageScore
        + ", weight=" + weight + "]";
  }

}
