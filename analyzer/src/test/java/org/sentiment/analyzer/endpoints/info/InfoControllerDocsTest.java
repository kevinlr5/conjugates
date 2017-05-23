package org.sentiment.analyzer.endpoints.info;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.Test;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.sentiment.analyzer.framework.TestHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;

public class InfoControllerDocsTest extends AnalyzerIntegrationBaseTest {

  @Rule
  public JUnitRestDocumentation restDocumentation =
      new JUnitRestDocumentation(AnalyzerIntegrationBaseTest.ASCIIDOCTOR_SNIPPETS_DIR);

  @Autowired
  private TestHttpService http;

  @Test
  public void infoExample() throws Exception {
    http.mvcDocs(restDocumentation).perform(get("/api/info"))
        .andExpect(status().isOk())
        .andDo(http.document(
            responseFields(
                fieldWithPath("name").description("The name of the service"),
                fieldWithPath("version").description("The version of the service"),
                fieldWithPath("commitHash").description("The commit built"))));
  }

}
