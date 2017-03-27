package org.conjugates.analyzer.services.documents;

import java.util.Set;

public class ParsedSentence {

  private final String sentence;
  private final Sentiment sentiment;
  private final Set<Entity> entities;

  public ParsedSentence(
      String sentence,
      Sentiment sentiment,
      Set<Entity> entities) {
    this.sentence = sentence;
    this.sentiment = sentiment;
    this.entities = entities;
  }

  public String getSentence() {
    return sentence;
  }

  public Sentiment getSentiment() {
    return sentiment;
  }

  public Set<Entity> getEntities() {
    return entities;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((entities == null) ? 0 : entities.hashCode());
    result = prime * result + ((sentence == null) ? 0 : sentence.hashCode());
    result = prime * result + ((sentiment == null) ? 0 : sentiment.hashCode());
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
    ParsedSentence other = (ParsedSentence) obj;
    if (entities == null) {
      if (other.entities != null) {
        return false;
      }
    } else if (!entities.equals(other.entities)) {
      return false;
    }
    if (sentence == null) {
      if (other.sentence != null) {
        return false;
      }
    } else if (!sentence.equals(other.sentence)) {
      return false;
    }
    if (sentiment != other.sentiment) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SentenceWithSentiment [sentence=" + sentence + ", sentiment=" + sentiment
        + ", entities=" + entities + "]";
  }

}
