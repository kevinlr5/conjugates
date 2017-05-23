package org.sentiment.analyzer.services.documents.stanford.analzyer;

import edu.stanford.nlp.simple.Document;

import org.sentiment.analyzer.services.documents.AnalyzedDocument;
import org.sentiment.analyzer.services.documents.DocumentAnalyzer;
import org.sentiment.analyzer.services.documents.DocumentScore;
import org.sentiment.analyzer.services.documents.ParsedDocument;
import org.sentiment.analyzer.services.documents.scorer.DocumentScorer;
import org.sentiment.analyzer.services.documents.stanford.parser.DocumentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StanfordNlpDocumentAnalyzer implements DocumentAnalyzer {

  private final DocumentParser parser;
  private final DocumentScorer scorer;

  @Autowired
  public StanfordNlpDocumentAnalyzer(DocumentParser parser, DocumentScorer scorer) {
    this.parser = parser;
    this.scorer = scorer;
  }

  @Override
  public AnalyzedDocument analyze(String text) {
    Document document = new Document(text);
    ParsedDocument parsedDocument = parser.parse(document);
    DocumentScore score = scorer.score(parsedDocument);
    return new AnalyzedDocument(parsedDocument, score);
  }

}
