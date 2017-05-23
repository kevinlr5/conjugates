package org.sentiment.analyzer.endpoints.document;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.Test;
import org.sentiment.analyzer.endpoints.document.AnalyzedDocumentRequest;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.sentiment.analyzer.framework.TestHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;

public class DocumentControllerDocsTest extends AnalyzerIntegrationBaseTest {

  @Rule
  public JUnitRestDocumentation restDocumentation =
      new JUnitRestDocumentation(AnalyzerIntegrationBaseTest.ASCIIDOCTOR_SNIPPETS_DIR);

  @Autowired
  private TestHttpService http;

  @Test
  public void documentExample() throws Exception {
    String text = "Schaaf said that Davis had refused to meet with her over the past year.";
    AnalyzedDocumentRequest request = new AnalyzedDocumentRequest(text);
    http.mvcDocs(restDocumentation)
        .perform(post("/api/document/analyze").contentType(MediaType.APPLICATION_JSON)
            .content(http.serialize(request)))
        .andExpect(status().isOk())
        .andDo(http.document(
            responseFields(
                fieldWithPath("score").description("The score for the provided document"))));
  }

}
