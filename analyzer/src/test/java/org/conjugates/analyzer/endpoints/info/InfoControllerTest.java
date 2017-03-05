package org.conjugates.analyzer.endpoints.info;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.conjugates.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.conjugates.analyzer.framework.TestHttpService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class InfoControllerTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private TestHttpService http;

  @Test
  public void testInfo() throws Exception {
    MvcResult result = http.mvc()
        .perform(get("/api/info/"))
        .andExpect(status().isOk())
        .andReturn();
    InfoResponse response = http.deserialize(result, InfoResponse.class);
    InfoResponse expected = new InfoResponse("Analyzer", "development", "development");
    Assert.assertEquals(expected, response);
  }

  @Test
  public void testUnauthorized() throws Exception {
    http.mvc()
        .perform(get("/api/info/unauthorized"))
        .andExpect(status().isUnauthorized())
        .andReturn();
  }

  @Test
  public void testPost() throws Exception {
    InfoTestPostRequest request = new InfoTestPostRequest("testRequest");
    http.mvc()
        .perform(post("/api/info/testpost").contentType(MediaType.APPLICATION_JSON)
            .content(http.serialize(request)))
        .andExpect(status().isOk())
        .andReturn();
  }

}
