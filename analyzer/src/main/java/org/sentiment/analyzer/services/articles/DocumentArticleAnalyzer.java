package org.sentiment.analyzer.services.articles;

import org.sentiment.analyzer.services.documents.AnalyzedDocument;
import org.sentiment.analyzer.services.documents.DocumentAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentArticleAnalyzer implements ArticleAnalyzer {

  private final DocumentAnalyzer documentAnalyzer;

  @Autowired
  public DocumentArticleAnalyzer(DocumentAnalyzer documentAnalyzer) {
    this.documentAnalyzer = documentAnalyzer;
  }

  @Override
  public AnalyzedArticle analyze(AnalyzedArticleRequest article) {
    AnalyzedDocument titleAnalysis = documentAnalyzer.analyze(article.getTitle());
    AnalyzedDocument bodyAnalysis = documentAnalyzer.analyze(article.getBody());
    return new AnalyzedArticle(titleAnalysis, bodyAnalysis);
  }

}
