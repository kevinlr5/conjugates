package org.conjugates.analyzer.services.documents.stanford.analzyer;

import edu.stanford.nlp.simple.Document;

import org.conjugates.analyzer.services.documents.AnalyzedDocument;
import org.conjugates.analyzer.services.documents.DocumentAnalyzer;
import org.conjugates.analyzer.services.documents.DocumentScore;
import org.conjugates.analyzer.services.documents.ParsedDocument;
import org.conjugates.analyzer.services.documents.scorer.DocumentScorer;
import org.conjugates.analyzer.services.documents.stanford.parser.DocumentParser;
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
