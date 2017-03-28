package org.conjugates.analyzer.endpoints.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.conjugates.analyzer.services.documents.AnalyzedDocument;
import org.conjugates.analyzer.services.documents.DocumentScore;

public class AnalyzedDocumentResponse {

  private final DocumentScore score;

  public static AnalyzedDocumentResponse from(AnalyzedDocument analyzedDocument) {
    return new AnalyzedDocumentResponse(analyzedDocument.getScore());
  }

  @JsonCreator
  public AnalyzedDocumentResponse(@JsonProperty("score") DocumentScore score) {
    this.score = score;
  }

  public DocumentScore getScore() {
    return score;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((score == null) ? 0 : score.hashCode());
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
    AnalyzedDocumentResponse other = (AnalyzedDocumentResponse) obj;
    if (score == null) {
      if (other.score != null) {
        return false;
      }
    } else if (!score.equals(other.score)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "AnalyzedDocumentResponse [score=" + score + "]";
  }

}
