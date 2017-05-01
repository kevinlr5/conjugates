package org.conjugates.analyzer.endpoints.article;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.io.Resources;

import java.nio.charset.Charset;

import org.conjugates.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.conjugates.analyzer.framework.TestHttpService;
import org.conjugates.analyzer.services.articles.AnalyzedArticleRequest;
import org.conjugates.analyzer.services.documents.DocumentAnalyzerTest;
import org.junit.Assert;
import org.junit.Test;
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
    AnalyzedArticleRequest request = new AnalyzedArticleRequest("Oakland Raiders", text);
    MvcResult result = http.mvc()
        .perform(post("/api/article/analyze").contentType(MediaType.APPLICATION_JSON)
            .content(http.serialize(request)))
        .andExpect(status().isOk())
        .andReturn();
    AnalyzedArticleResponse response = http.deserialize(result, AnalyzedArticleResponse.class);

    int expectedTitleWeight = 1;
    int expectedTitleScore = 50;
    Assert.assertEquals(expectedTitleWeight, response.getTitleScore().getWeight());
    Assert.assertEquals(expectedTitleScore, response.getTitleScore().getAverageScore());

    int expectedBodyWeight = 35;
    int expectedBodyScore = 34;
    Assert.assertEquals(expectedBodyWeight, response.getBodyScore().getWeight());
    Assert.assertEquals(expectedBodyScore, response.getBodyScore().getAverageScore());
  }

}
