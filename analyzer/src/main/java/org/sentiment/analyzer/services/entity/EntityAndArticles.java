package org.sentiment.analyzer.services.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import org.sentiment.analyzer.services.documents.Entity;

public class EntityAndArticles {

  private final Entity entity;
  private final int averageScore;
  private final int numberOfMentions;
  private final List<ArticleReference> articles;

  @JsonCreator
  public EntityAndArticles(
      @JsonProperty("entity") Entity entity,
      @JsonProperty("averageScore") int averageScore,
      @JsonProperty("numberOfMentions") int numberOfMentions,
      @JsonProperty("articles") List<ArticleReference> articles) {
    this.entity = entity;
    this.averageScore = averageScore;
    this.numberOfMentions = numberOfMentions;
    this.articles = articles;
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

  public List<ArticleReference> getArticles() {
    return articles;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((articles == null) ? 0 : articles.hashCode());
    result = prime * result + averageScore;
    result = prime * result + ((entity == null) ? 0 : entity.hashCode());
    result = prime * result + numberOfMentions;
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
    EntityAndArticles other = (EntityAndArticles) obj;
    if (articles == null) {
      if (other.articles != null) {
        return false;
      }
    } else if (!articles.equals(other.articles)) {
      return false;
    }
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
    return true;
  }

  @Override
  public String toString() {
    return "EntityAndArticles [entity=" + entity + ", averageScore=" + averageScore
        + ", numberOfMentions=" + numberOfMentions + ", articles=" + articles + "]";
  }

}
