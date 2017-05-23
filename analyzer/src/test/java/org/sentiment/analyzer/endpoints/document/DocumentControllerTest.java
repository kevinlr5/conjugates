package org.sentiment.analyzer.endpoints.document;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.io.Resources;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;
import org.sentiment.analyzer.endpoints.document.AnalyzedDocumentRequest;
import org.sentiment.analyzer.endpoints.document.AnalyzedDocumentResponse;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.sentiment.analyzer.framework.TestHttpService;
import org.sentiment.analyzer.services.documents.DocumentAnalyzerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class DocumentControllerTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private TestHttpService http;

  @Test
  public void testAnalyze() throws Exception {
    String text = Resources.toString(
        DocumentAnalyzerTest.class.getResource("/raiders.txt"),
        Charset.forName("UTF-8"));
    AnalyzedDocumentRequest request = new AnalyzedDocumentRequest(text);
    MvcResult result = http.mvc()
        .perform(post("/api/document/analyze").contentType(MediaType.APPLICATION_JSON)
            .content(http.serialize(request)))
        .andExpect(status().isOk())
        .andReturn();
    AnalyzedDocumentResponse response = http.deserialize(result, AnalyzedDocumentResponse.class);

    int expectedWeight = 35;
    int expectedScore = 34;
    Assert.assertEquals(expectedWeight, response.getScore().getWeight());
    Assert.assertEquals(expectedScore, response.getScore().getAverageScore());
  }
}
