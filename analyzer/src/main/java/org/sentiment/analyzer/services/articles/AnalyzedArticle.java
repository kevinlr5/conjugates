package org.sentiment.analyzer.services.articles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.sentiment.analyzer.services.documents.DocumentScore;

public class AnalyzedArticle {

  private final long id;
  private final String title;
  private final DocumentScore titleScore;
  private final DocumentScore bodyScore;

  @JsonCreator
  public AnalyzedArticle(
      @JsonProperty("id") long id,
      @JsonProperty("title") String title,
      @JsonProperty("titleScore") DocumentScore titleScore,
      @JsonProperty("bodyScore") DocumentScore bodyScore) {
    this.id = id;
    this.title = title;
    this.titleScore = titleScore;
    this.bodyScore = bodyScore;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public DocumentScore getTitleScore() {
    return titleScore;
  }

  public DocumentScore getBodyScore() {
    return bodyScore;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bodyScore == null) ? 0 : bodyScore.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((titleScore == null) ? 0 : titleScore.hashCode());
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
    AnalyzedArticle other = (AnalyzedArticle) obj;
    if (bodyScore == null) {
      if (other.bodyScore != null) {
        return false;
      }
    } else if (!bodyScore.equals(other.bodyScore)) {
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
    if (titleScore == null) {
      if (other.titleScore != null) {
        return false;
      }
    } else if (!titleScore.equals(other.titleScore)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "AnalyzedArticle [id=" + id + ", title=" + title + ", titleScore=" + titleScore
        + ", bodyScore=" + bodyScore + "]";
  }

}
