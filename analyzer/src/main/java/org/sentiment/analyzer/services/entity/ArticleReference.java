package org.sentiment.analyzer.services.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.sentiment.analyzer.services.documents.EntityScore;

public class ArticleReference {

  private final long id;
  private final String title;
  private final EntityScore entityScore;

  @JsonCreator
  public ArticleReference(
      @JsonProperty("id") long id,
      @JsonProperty("title") String title,
      @JsonProperty("entityScore") EntityScore entityScore) {
    this.id = id;
    this.title = title;
    this.entityScore = entityScore;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public EntityScore getEntityScore() {
    return entityScore;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((entityScore == null) ? 0 : entityScore.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((title == null) ? 0 : title.hashCode());
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
    ArticleReference other = (ArticleReference) obj;
    if (entityScore == null) {
      if (other.entityScore != null) {
        return false;
      }
    } else if (!entityScore.equals(other.entityScore)) {
      return false;
    }
    if (id != other.id) {
      return false;
    }
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ArticleReference [id=" + id + ", title=" + title + ", entityScore=" + entityScore + "]";
  }

}
