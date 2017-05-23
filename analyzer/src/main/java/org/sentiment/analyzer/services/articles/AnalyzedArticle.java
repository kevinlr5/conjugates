package org.sentiment.analyzer.services.articles;

import org.sentiment.analyzer.services.documents.AnalyzedDocument;

public class AnalyzedArticle {

  private final AnalyzedDocument titleAnalysis;
  private final AnalyzedDocument bodyAnalysis;

  public AnalyzedArticle(AnalyzedDocument titleAnalysis, AnalyzedDocument bodyAnalysis) {
    this.titleAnalysis = titleAnalysis;
    this.bodyAnalysis = bodyAnalysis;
  }

  public AnalyzedDocument getTitleAnalysis() {
    return titleAnalysis;
  }

  public AnalyzedDocument getBodyAnalysis() {
    return bodyAnalysis;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bodyAnalysis == null) ? 0 : bodyAnalysis.hashCode());
    result = prime * result + ((titleAnalysis == null) ? 0 : titleAnalysis.hashCode());
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
    if (bodyAnalysis == null) {
      if (other.bodyAnalysis != null) {
        return false;
      }
    } else if (!bodyAnalysis.equals(other.bodyAnalysis)) {
      return false;
    }
    if (titleAnalysis == null) {
      if (other.titleAnalysis != null) {
        return false;
      }
    } else if (!titleAnalysis.equals(other.titleAnalysis)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "AnalyzedArticle [titleAnalysis=" + titleAnalysis + ", bodyAnalysis=" + bodyAnalysis
        + "]";
  }

}
