package org.conjugates.analyzer.endpoints.article;

import org.conjugates.analyzer.services.articles.AnalyzedArticle;
import org.conjugates.analyzer.services.articles.AnalyzedArticleRequest;
import org.conjugates.analyzer.services.articles.ArticleAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/article")
@RestController
public class ArticleController {

  private final ArticleAnalyzer articleAnalyzer;

  @Autowired
  public ArticleController(ArticleAnalyzer articleAnalyzer) {
    this.articleAnalyzer = articleAnalyzer;
  }

  @RequestMapping(value = "/analyze", method = RequestMethod.POST)
  @ResponseBody
  public AnalyzedArticleResponse analyze(@RequestBody AnalyzedArticleRequest request) {
    AnalyzedArticle analyzedArticle = articleAnalyzer.analyze(request);
    return AnalyzedArticleResponse.from(analyzedArticle);
  }

}
