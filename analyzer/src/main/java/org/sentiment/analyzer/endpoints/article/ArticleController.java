package org.sentiment.analyzer.endpoints.article;

import org.sentiment.analyzer.services.articles.AnalyzedArticle;
import org.sentiment.analyzer.services.articles.AnalyzedArticleRequest;
import org.sentiment.analyzer.services.articles.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/article")
@RestController
public class ArticleController {

  private final ArticleService articleService;

  @Autowired
  public ArticleController(ArticleService articleAnalyzer) {
    this.articleService = articleAnalyzer;
  }

  @RequestMapping(value = "/analyze", method = RequestMethod.POST)
  @ResponseBody
  public AnalyzedArticle analyze(@RequestBody AnalyzedArticleRequest request) {
    return articleService.analyze(request);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public AnalyzedArticle get(@PathVariable("id") String id) {
    return articleService.get(Long.valueOf(id));
  }

}
