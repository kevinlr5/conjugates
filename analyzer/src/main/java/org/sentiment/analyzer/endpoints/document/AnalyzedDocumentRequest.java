package org.sentiment.analyzer.endpoints.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalyzedDocumentRequest {

  private final String text;

  @JsonCreator
  public AnalyzedDocumentRequest(@JsonProperty("text") String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((text == null) ? 0 : text.hashCode());
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
    AnalyzedDocumentRequest other = (AnalyzedDocumentRequest) obj;
    if (text == null) {
      if (other.text != null) {
        return false;
      }
    } else if (!text.equals(other.text)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "AnalyzedDocumentRequest [text=" + text + "]";
  }

}
