package org.sentiment.analyzer.endpoints.entity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.collect.Iterables;
import com.google.common.io.Resources;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.sentiment.analyzer.framework.TestHttpService;
import org.sentiment.analyzer.services.articles.AnalyzedArticle;
import org.sentiment.analyzer.services.articles.AnalyzedArticleRequest;
import org.sentiment.analyzer.services.articles.ArticleService;
import org.sentiment.analyzer.services.documents.DocumentAnalyzerTest;
import org.sentiment.analyzer.services.documents.Entity;
import org.sentiment.analyzer.services.entity.ArticleReference;
import org.sentiment.analyzer.services.entity.EntityAndArticles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

public class EntityControllerTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private TestHttpService http;
  @Autowired
  private ArticleService articleService;

  @Test
  public void testGet() throws Exception {
    String text = Resources.toString(
        DocumentAnalyzerTest.class.getResource("/strickland.txt"),
        Charset.forName("UTF-8"));
    String title = "Some Title";
    AnalyzedArticleRequest request = new AnalyzedArticleRequest(title, text);
    AnalyzedArticle article = articleService.analyze(request);

    MvcResult result = http.mvc()
        .perform(get("/api/entity/strickland"))
        .andExpect(status().isOk())
        .andReturn();
    EntityAndArticles response = http.deserialize(result, EntityAndArticles.class);

    Entity entity = response.getEntity();
    Assert.assertEquals("strickland", entity.getValue());
    Assert.assertEquals("Strickland", entity.getRawValue());
    Assert.assertEquals("PERSON", entity.getType());

    int averageScore = response.getAverageScore();
    Assert.assertEquals(35, averageScore);

    int numMentions = response.getNumberOfMentions();
    Assert.assertEquals(10, numMentions);

    ArticleReference ref = Iterables.getOnlyElement(response.getArticles());
    Assert.assertEquals(article.getId(), ref.getArticleId());
    Assert.assertEquals(title, ref.getTitle());
    Assert.assertEquals(35, ref.getEntityScore().getAverageScore());
  }

}
