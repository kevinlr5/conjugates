package org.conjugates.analyzer.endpoints.document;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.common.io.Resources;

import java.nio.charset.Charset;

import org.conjugates.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.conjugates.analyzer.framework.TestHttpService;
import org.conjugates.analyzer.services.documents.DocumentAnalyzerTest;
import org.junit.Assert;
import org.junit.Test;
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
