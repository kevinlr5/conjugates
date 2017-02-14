package org.conjugates.analyzer.endpoints.info;

import org.conjugates.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.conjugates.analyzer.framework.TestHttpService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class InfoControllerTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private TestHttpService http;

  @Test
  public void testInfo() throws Exception {
    MvcResult result = http.mvc()
        .perform(MockMvcRequestBuilders.get("/api/info/").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String content = result.getResponse().getContentAsString();
    System.out.println(content);
  }

  @Test
  public void testUnauthorized() throws Exception {
    http.mvc()
        .perform(
            MockMvcRequestBuilders.get("/api/info/unauthorized").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();
  }

  @Test
  public void testPost() throws Exception {
    http.mvc()
        .perform(
            MockMvcRequestBuilders.post("/api/info/testpost").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
  }

}
