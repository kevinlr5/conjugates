package org.conjugates.analyzer.services.documents;

public class AnalyzedDocument {

  private final ParsedDocument parsedDocument;
  private final DocumentScore score;

  public AnalyzedDocument(ParsedDocument parsedDocument, DocumentScore score) {
    this.parsedDocument = parsedDocument;
    this.score = score;
  }

  public ParsedDocument getParsedDocument() {
    return parsedDocument;
  }

  public DocumentScore getScore() {
    return score;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((parsedDocument == null) ? 0 : parsedDocument.hashCode());
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
    AnalyzedDocument other = (AnalyzedDocument) obj;
    if (parsedDocument == null) {
      if (other.parsedDocument != null) {
        return false;
      }
    } else if (!parsedDocument.equals(other.parsedDocument)) {
      return false;
    }
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
    return "AnalyzedDocument [parsedDocument=" + parsedDocument + ", score=" + score + "]";
  }

}
