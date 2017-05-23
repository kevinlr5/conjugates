package org.sentiment.analyzer.endpoints.info;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.sentiment.analyzer.endpoints.info.InfoResponse;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.sentiment.analyzer.framework.TestHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

public class InfoControllerTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private TestHttpService http;

  @Test
  public void testInfo() throws Exception {
    MvcResult result = http.mvc()
        .perform(get("/api/info"))
        .andExpect(status().isOk())
        .andReturn();
    InfoResponse response = http.deserialize(result, InfoResponse.class);
    InfoResponse expected = new InfoResponse("Analyzer", "development", "development");
    Assert.assertEquals(expected, response);
  }

}
