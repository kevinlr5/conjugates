package org.conjugates.analyzer.services.articles;

import com.google.common.collect.Iterables;

import org.conjugates.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.conjugates.analyzer.services.documents.AnalyzedDocument;
import org.conjugates.analyzer.services.documents.DocumentScore;
import org.conjugates.analyzer.services.documents.Entity;
import org.conjugates.analyzer.services.documents.EntityScore;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleAnalyzerTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private ArticleAnalyzer articleAnalyzer;

  @Test
  public void testArticleAnalysis() {
    String title = createTestTitle();
    String body = createTestBody();
    AnalyzedArticle result = articleAnalyzer.analyze(new AnalyzedArticleRequest(title, body));

    AnalyzedDocument titleResult = result.getTitleAnalysis();

    DocumentScore titleDocumentScore = titleResult.getScore();
    Assert.assertEquals(75, titleDocumentScore.getAverageScore());
    Assert.assertEquals(1, titleDocumentScore.getWeight());

    EntityScore titleEntityScore =
        Iterables.getOnlyElement(titleResult.getScore().getEntityScores());
    Assert.assertEquals(75, titleEntityScore.getAverageScore());
    Assert.assertEquals(1, titleEntityScore.getNumberOfMentions());
    Assert.assertEquals(1, titleEntityScore.getWeight());

    Entity titleExpectedEntity = createTestTitleEntity();
    Assert.assertEquals(titleExpectedEntity, titleEntityScore.getEntity());

    AnalyzedDocument bodyResult = result.getBodyAnalysis();

    DocumentScore bodyDocumentScore = bodyResult.getScore();
    Assert.assertEquals(25, bodyDocumentScore.getAverageScore());
    Assert.assertEquals(1, bodyDocumentScore.getWeight());

    EntityScore bodyEntityScore = Iterables.getOnlyElement(bodyResult.getScore().getEntityScores());
    Assert.assertEquals(25, bodyEntityScore.getAverageScore());
    Assert.assertEquals(1, bodyEntityScore.getNumberOfMentions());
    Assert.assertEquals(1, bodyEntityScore.getWeight());

    Entity bodyExpectedEntity = createTestBodyEntity();
    Assert.assertEquals(bodyExpectedEntity, bodyEntityScore.getEntity());
  }

  private static String createTestTitle() {
    return "Jim Doe is a good person.";
  }

  private static Entity createTestTitleEntity() {
    return new Entity("Jim Doe", "PERSON");
  }

  private static String createTestBody() {
    return "Jim Smith went across the street to cut down a tree.";
  }

  private static Entity createTestBodyEntity() {
    return new Entity("Jim Smith", "PERSON");
  }

}
