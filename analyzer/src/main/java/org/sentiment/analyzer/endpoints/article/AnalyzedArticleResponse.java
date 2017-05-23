package org.sentiment.analyzer.endpoints.article;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.sentiment.analyzer.services.articles.AnalyzedArticle;
import org.sentiment.analyzer.services.documents.DocumentScore;

public class AnalyzedArticleResponse {

  private final DocumentScore titleScore;
  private final DocumentScore bodyScore;

  public static AnalyzedArticleResponse from(AnalyzedArticle analyzedArticle) {
    return new AnalyzedArticleResponse(
        analyzedArticle.getTitleAnalysis().getScore(),
        analyzedArticle.getBodyAnalysis().getScore());
  }

  @JsonCreator
  public AnalyzedArticleResponse(
      @JsonProperty("titleScore") DocumentScore titleScore,
      @JsonProperty("bodyScore") DocumentScore bodyScore) {
    this.titleScore = titleScore;
    this.bodyScore = bodyScore;
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
    AnalyzedArticleResponse other = (AnalyzedArticleResponse) obj;
    if (bodyScore == null) {
      if (other.bodyScore != null) {
        return false;
      }
    } else if (!bodyScore.equals(other.bodyScore)) {
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
    return "AnalyzedArticleResponse [titleScore=" + titleScore + ", bodyScore=" + bodyScore + "]";
  }

}
