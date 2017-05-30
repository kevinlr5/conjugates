package org.sentiment.analyzer.services.documents.stanford.parser;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.SentimentClass;

import java.util.List;
import java.util.Set;

import org.sentiment.analyzer.services.documents.Entity;
import org.sentiment.analyzer.services.documents.ParsedSentence;
import org.sentiment.analyzer.services.documents.Sentiment;
import org.springframework.stereotype.Service;

@Service
public class SentenceParser {

  private static final String DISALLOWED_DEFAULT_TAG = "O";

  public ParsedSentence parse(Sentence sentence) {
    String rawSentence = sentence.text();
    Sentiment sentiment = getSentiment(sentence);
    Set<Entity> entities = getEntities(sentence);
    return new ParsedSentence(rawSentence, sentiment, entities);
  }

  private static Sentiment getSentiment(Sentence sentence) {
    SentimentClass sentimentClass = sentence.sentiment();
    if (sentimentClass == SentimentClass.VERY_NEGATIVE) {
      return Sentiment.VERY_NEGATIVE;
    } else if (sentimentClass == SentimentClass.NEGATIVE) {
      return Sentiment.NEGATIVE;
    } else if (sentimentClass == SentimentClass.NEUTRAL) {
      return Sentiment.NEUTRAL;
    } else if (sentimentClass == SentimentClass.POSITIVE) {
      return Sentiment.POSITIVE;
    } else if (sentimentClass == SentimentClass.VERY_POSITIVE) {
      return Sentiment.VERY_POSITIVE;
    } else {
      throw new IllegalArgumentException("Unknown sentiment: " + sentimentClass);
    }
  }

  private static Set<Entity> getEntities(Sentence sentence) {
    Set<String> nerTags = FluentIterable.from(sentence.nerTags())
        .filter(s -> !s.equals(DISALLOWED_DEFAULT_TAG))
        .toSet();
    return FluentIterable.from(nerTags)
        .transformAndConcat(t -> getEntitiesAndTypesForTag(t, sentence))
        .toSet();
  }

  private static List<Entity> getEntitiesAndTypesForTag(String tag, Sentence sentence) {
    return Lists.transform(
        sentence.mentions(tag),
        mention -> new Entity(mention.trim().replaceAll(" ", "_").toLowerCase(), mention, tag));
  }

}
