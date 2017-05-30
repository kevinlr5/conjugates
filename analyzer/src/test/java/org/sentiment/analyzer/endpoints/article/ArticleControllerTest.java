package org.sentiment.analyzer.endpoints.article;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.io.Resources;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.sentiment.analyzer.framework.TestHttpService;
import org.sentiment.analyzer.services.articles.AnalyzedArticle;
import org.sentiment.analyzer.services.articles.AnalyzedArticleRequest;
import org.sentiment.analyzer.services.documents.DocumentAnalyzerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class ArticleControllerTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private TestHttpService http;

  @Test
  public void testAnalyze() throws Exception {
    String text = Resources.toString(
        DocumentAnalyzerTest.class.getResource("/raiders.txt"),
        Charset.forName("UTF-8"));
    String title = "Oakland Raiders";
    AnalyzedArticleRequest request = new AnalyzedArticleRequest(title, text);
    MvcResult result = http.mvc()
        .perform(post("/api/article/analyze").contentType(MediaType.APPLICATION_JSON)
            .content(http.serialize(request)))
        .andExpect(status().isOk())
        .andReturn();
    AnalyzedArticle response = http.deserialize(result, AnalyzedArticle.class);

    Assert.assertEquals(title, response.getTitle());

    int expectedTitleWeight = 1;
    int expectedTitleScore = 50;
    Assert.assertEquals(expectedTitleWeight, response.getTitleScore().getWeight());
    Assert.assertEquals(expectedTitleScore, response.getTitleScore().getAverageScore());

    int expectedBodyWeight = 35;
    int expectedBodyScore = 34;
    Assert.assertEquals(expectedBodyWeight, response.getBodyScore().getWeight());
    Assert.assertEquals(expectedBodyScore, response.getBodyScore().getAverageScore());
  }

  @Test
  public void testGet() throws Exception {
    String text = Resources.toString(
        DocumentAnalyzerTest.class.getResource("/raiders.txt"),
        Charset.forName("UTF-8"));
    String title = "Different Title";
    AnalyzedArticleRequest request = new AnalyzedArticleRequest(title, text);
    MvcResult postResult = http.mvc()
        .perform(post("/api/article/analyze").contentType(MediaType.APPLICATION_JSON)
            .content(http.serialize(request)))
        .andExpect(status().isOk())
        .andReturn();
    AnalyzedArticle postResponse = http.deserialize(postResult, AnalyzedArticle.class);

    MvcResult getResult = http.mvc()
        .perform(get("/api/article/" + postResponse.getId()))
        .andExpect(status().isOk())
        .andReturn();
    AnalyzedArticle getResponse = http.deserialize(getResult, AnalyzedArticle.class);

    Assert.assertEquals(postResponse, getResponse);
  }

}
