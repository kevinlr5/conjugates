package org.conjugates.analyzer.services.documents;

import com.google.common.collect.Iterables;
import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.Charset;

import org.conjugates.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentAnalyzerTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private DocumentAnalyzer documentAnalyzer;

  @Test
  public void testScore() {
    String text = createTestString();
    AnalyzedDocument result = documentAnalyzer.analyze(text);

    DocumentScore documentScore = result.getScore();
    Assert.assertEquals(25, documentScore.getAverageScore());
    Assert.assertEquals(1, documentScore.getWeight());

    EntityScore entityScore = Iterables.getOnlyElement(result.getScore().getEntityScores());
    Assert.assertEquals(25, entityScore.getAverageScore());
    Assert.assertEquals(1, entityScore.getNumberOfMentions());
    Assert.assertEquals(1, entityScore.getWeight());

    Entity expectedEntity = createTestEntity();
    Assert.assertEquals(expectedEntity, entityScore.getEntity());
  }

  @Test
  public void testParsedDocument() {
    String text = createTestString();
    AnalyzedDocument result = documentAnalyzer.analyze(text);

    ParsedDocument parsedDocument = result.getParsedDocument();
    Assert.assertEquals(createTestString(), parsedDocument.getText());

    ParsedSentence sentence = Iterables.getOnlyElement(parsedDocument.getSentences());
    Assert.assertEquals(createTestString(), sentence.getSentence());
    Assert.assertEquals(Sentiment.NEGATIVE, sentence.getSentiment());

    Entity actualEntity = Iterables.getOnlyElement(sentence.getEntities());
    Assert.assertEquals(createTestEntity(), actualEntity);
  }

  @Test
  public void testLoadedDocumentScore() throws IOException {
    String text = Resources.toString(
        DocumentAnalyzerTest.class.getResource("/raiders.txt"),
        Charset.forName("UTF-8"));
    AnalyzedDocument result = documentAnalyzer.analyze(text);

    int expectedWeight = 35;
    int expectedScore = 34;
    Assert.assertEquals(expectedWeight, result.getScore().getWeight());
    Assert.assertEquals(expectedScore, result.getScore().getAverageScore());
  }

  private static String createTestString() {
    return "Jim Smith went across the street to cut down a tree.";
  }

  private static Entity createTestEntity() {
    return new Entity("Jim Smith", "PERSON");
  }

}
