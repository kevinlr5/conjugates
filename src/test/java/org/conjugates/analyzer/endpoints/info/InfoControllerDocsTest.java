package org.conjugates.analyzer.endpoints.info;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.conjugates.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.conjugates.analyzer.framework.TestHttpService;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;

public class InfoControllerDocsTest extends AnalyzerIntegrationBaseTest {

  @Rule
  public JUnitRestDocumentation restDocumentation =
      new JUnitRestDocumentation("build/generated-snippets");

  @Autowired
  private TestHttpService http;

  @Test
  public void infoExample() throws Exception {
    http.mvcDocs(restDocumentation).perform(get("/api/info/"))
        .andExpect(status().isOk())
        .andDo(http.document(
            responseFields(
                fieldWithPath("name").description("The name of the service"),
                fieldWithPath("version").description("The version of the service"))));
  }

  @Test
  public void infoUnauthorizedExample() throws Exception {
    http.mvcDocs(restDocumentation)
        .perform(get("/api/info/unauthorized"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void infoTestpostExample() throws Exception {
    InfoTestPostRequest request = new InfoTestPostRequest("test request");
    http.mvcDocs(restDocumentation)
        .perform(post("/api/info/testpost").contentType(MediaType.APPLICATION_JSON)
            .content(http.serialize(request)))
        .andExpect(status().isOk())
        .andDo(http.document(
            requestFields(
                fieldWithPath("value").description("Value to print in the log"))));
  }
}
