package org.conjugates.analyzer.services.documents;

import java.util.List;

public class ParsedDocument {

  private final String text;
  private final List<ParsedSentence> sentences;

  public ParsedDocument(String text, List<ParsedSentence> sentences) {
    this.text = text;
    this.sentences = sentences;
  }

  public String getText() {
    return text;
  }

  public List<ParsedSentence> getSentences() {
    return sentences;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((sentences == null) ? 0 : sentences.hashCode());
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
    ParsedDocument other = (ParsedDocument) obj;
    if (sentences == null) {
      if (other.sentences != null) {
        return false;
      }
    } else if (!sentences.equals(other.sentences)) {
      return false;
    }
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
    return "AnalyzedDocument [text=" + text + ", sentences=" + sentences + "]";
  }

}
