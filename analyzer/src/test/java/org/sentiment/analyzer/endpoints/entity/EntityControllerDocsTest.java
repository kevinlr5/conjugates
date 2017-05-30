package org.sentiment.analyzer.endpoints.entity;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.Test;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.sentiment.analyzer.framework.TestHttpService;
import org.sentiment.analyzer.services.articles.AnalyzedArticleRequest;
import org.sentiment.analyzer.services.articles.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;

public class EntityControllerDocsTest extends AnalyzerIntegrationBaseTest {

  @Rule
  public JUnitRestDocumentation restDocumentation =
      new JUnitRestDocumentation(AnalyzerIntegrationBaseTest.ASCIIDOCTOR_SNIPPETS_DIR);

  @Autowired
  private TestHttpService http;
  @Autowired
  private ArticleService articleService;

  @Test
  public void testGet() throws Exception {
    String title = "Raiders Leaving Oakland";
    String body = "Schaaf said that Davis had refused to meet with her over the past year.";
    AnalyzedArticleRequest request = new AnalyzedArticleRequest(title, body);
    articleService.analyze(request);
    http.mvcDocs(restDocumentation)
        .perform(get("/api/entity/davis"))
        .andExpect(status().isOk())
        .andDo(http.document(
            responseFields(
                fieldWithPath("entity").description("The information about the specified entity"),
                fieldWithPath("averageScore").description("The average score for the entity"),
                fieldWithPath("numberOfMentions").description("The aggregate number of mentions"),
                fieldWithPath("articles").description("The articles that reference the entity"))));
  }

}
