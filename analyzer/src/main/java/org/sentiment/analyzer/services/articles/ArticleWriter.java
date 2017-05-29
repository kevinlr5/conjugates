package org.sentiment.analyzer.services.articles;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import java.util.List;

import org.sentiment.analyzer.services.documents.DocumentScore;
import org.sentiment.analyzer.services.documents.EntityScore;
import org.sentiment.analyzer.services.sql.PreparableBatchStatement;
import org.sentiment.analyzer.services.sql.PreparableStatement;
import org.sentiment.analyzer.services.sql.SqlDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleWriter {

  private static final String INSERT_ARTICLE =
      " INSERT INTO article ( "
      + "   title "
      + " ) VALUES ( "
      + "   ? "
      + " ) ";
  private static final String INSERT_DOCUMENT_SCORE =
      " INSERT INTO document_score ( "
      + "   type, "
      + "   average_score, "
      + "   weight, "
      + "   article_id"
      + " ) VALUES ( "
      + "   ?, "
      + "   ?, "
      + "   ?, "
      + "   ?"
      + " ) ";
  private static final String INSERT_ENTITIES =
      " INSERT IGNORE INTO entity ("
      + "   entity_value, "
      + "   entity_value_raw, "
      + "   entity_type"
      + " ) VALUES ( "
      + "   ?, "
      + "   ?, "
      + "   ?"
      + " ) ";
  private static final String INSERT_ENTITY_SCORES =
      " INSERT INTO entity_score ("
      + "   entity_value, "
      + "   average_score, "
      + "   number_of_mentions, "
      + "   weight,"
      + "   article_id, "
      + "   document_score_id"
      + " ) VALUES ( "
      + "   ?, "
      + "   ?, "
      + "   ?, "
      + "   ?, "
      + "   ?, "
      + "   ? "
      + " ) ";


  private final SqlDatabaseService sqlService;

  @Autowired
  public ArticleWriter(SqlDatabaseService sqlService) {
    this.sqlService = sqlService;
  }

  public AnalyzedArticle storeArticleResults(
      String title,
      DocumentScore titleScore,
      DocumentScore bodyScore) {
    return sqlService.runInTransaction(statementService -> {
      long articleId = statementService.insertAndGetId(createArticle(title));
      statementService.insertBatch(createEntities(titleScore, bodyScore));
      long titleId = statementService.insertAndGetId(
          createDocumentScore(
              titleScore,
              "title",
              articleId));
      statementService.insertBatch(createEntityScores(titleScore, titleId, articleId));
      long bodyId = statementService.insertAndGetId(
          createDocumentScore(
              bodyScore,
              "body",
              articleId));
      statementService.insertBatch(createEntityScores(bodyScore, bodyId, articleId));
      return new AnalyzedArticle(articleId, title, titleScore, bodyScore);
    });
  }

  private static PreparableStatement createArticle(String title) {
    return new PreparableStatement(INSERT_ARTICLE, Lists.newArrayList(title));
  }

  private static PreparableStatement createDocumentScore(
      DocumentScore score,
      String type,
      long articleId) {
    return new PreparableStatement(
        INSERT_DOCUMENT_SCORE,
        Lists.newArrayList(type, score.getAverageScore(), score.getWeight(), articleId));
  }

  private static PreparableBatchStatement createEntities(
      DocumentScore titleScore,
      DocumentScore bodyScore) {
    List<List<Object>> entityParams = FluentIterable.concat(
        titleScore.getEntityScores(),
        bodyScore.getEntityScores())
        .transform(ArticleWriter::getEntityParams)
        .toList();
    return new PreparableBatchStatement(
        INSERT_ENTITIES,
        entityParams);
  }

  private static List<Object> getEntityParams(EntityScore es) {
    return Lists.newArrayList(
        es.getEntity().getValue(),
        es.getEntity().getRawValue(),
        es.getEntity().getType());
  }

  private static PreparableBatchStatement createEntityScores(
      DocumentScore documentScore,
      long documentScoreId,
      long articleId) {
    List<List<Object>> entityScoreParams = FluentIterable.from(documentScore.getEntityScores())
        .transform(es -> getEntityScoreParams(es, documentScoreId, articleId))
        .toList();
    return new PreparableBatchStatement(
        INSERT_ENTITY_SCORES,
        entityScoreParams);
  }

  private static List<Object> getEntityScoreParams(
      EntityScore es,
      long documentScoreId,
      long articleId) {
    return Lists.newArrayList(
        es.getEntity().getValue(),
        es.getAverageScore(),
        es.getNumberOfMentions(),
        es.getWeight(),
        articleId,
        documentScoreId);
  }

}
