package org.sentiment.analyzer.services.articles;

import org.sentiment.analyzer.services.documents.AnalyzedDocument;
import org.sentiment.analyzer.services.documents.DocumentAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentArticleAnalyzer implements ArticleService {

  private final DocumentAnalyzer documentAnalyzer;
  private final ArticleWriter articleStorer;
  private final ArticleLoader articleLoader;

  @Autowired
  public DocumentArticleAnalyzer(
      DocumentAnalyzer documentAnalyzer,
      ArticleWriter articleStorer,
      ArticleLoader articleLoader) {
    this.documentAnalyzer = documentAnalyzer;
    this.articleStorer = articleStorer;
    this.articleLoader = articleLoader;
  }

  @Override
  public AnalyzedArticle analyze(AnalyzedArticleRequest article) {
    AnalyzedDocument titleAnalysis = documentAnalyzer.analyze(article.getTitle());
    AnalyzedDocument bodyAnalysis = documentAnalyzer.analyze(article.getBody());
    return articleStorer.storeArticleResults(
        article.getTitle(),
        titleAnalysis.getScore(),
        bodyAnalysis.getScore());
  }

  @Override
  public AnalyzedArticle get(long id) {
    return articleLoader.load(id);
  }

}
