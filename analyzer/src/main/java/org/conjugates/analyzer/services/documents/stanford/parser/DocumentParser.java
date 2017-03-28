package org.conjugates.analyzer.services.documents.stanford.parser;

import com.google.common.collect.FluentIterable;

import edu.stanford.nlp.simple.Document;

import java.util.List;

import org.conjugates.analyzer.services.documents.ParsedDocument;
import org.conjugates.analyzer.services.documents.ParsedSentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentParser {

  private final SentenceParser sentenceParser;

  @Autowired
  public DocumentParser(SentenceParser sentenceParser) {
    this.sentenceParser = sentenceParser;
  }

  public ParsedDocument parse(Document document) {
    String text = document.text();
    List<ParsedSentence> sentences = FluentIterable.from(document.sentences())
        .transform(s -> sentenceParser.parse(s))
        .toList();
    return new ParsedDocument(text, sentences);
  }

}
