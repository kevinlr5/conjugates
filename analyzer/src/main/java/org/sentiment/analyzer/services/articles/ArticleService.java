package org.sentiment.analyzer.services.articles;

public interface ArticleService {

  AnalyzedArticle analyze(AnalyzedArticleRequest article);

  AnalyzedArticle get(long id);

}
