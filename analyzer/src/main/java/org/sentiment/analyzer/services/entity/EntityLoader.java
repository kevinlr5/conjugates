package org.sentiment.analyzer.services.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.sentiment.analyzer.services.documents.Entity;
import org.sentiment.analyzer.services.documents.EntityScore;
import org.sentiment.analyzer.services.sql.PreparableStatement;
import org.sentiment.analyzer.services.sql.SqlDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityLoader {

  private static final String ENTITY_QUERY = "SELECT * FROM entity WHERE entity_value = ?";
  private static final String ARTICLE_QUERY =
      " SELECT * FROM entity "
      + " INNER JOIN entity_score ON entity_score.entity_value = entity.entity_value "
      + " INNER JOIN article ON article.id = entity_score.article_id "
      + " WHERE entity.entity_value = ? ";

  private final SqlDatabaseService sqlService;

  @Autowired
  public EntityLoader(SqlDatabaseService sqlService) {
    this.sqlService = sqlService;
  }

  public EntityAndArticles get(String entityValue) {
    return sqlService.runInTransaction(statementService -> {
      Entity entity = Iterables.getOnlyElement(statementService.query(
          createEntity(entityValue),
          EntityLoader::convertToEntity));
      List<ArticleReference> articleReferences = statementService.query(
          createArticleReferences(entityValue),
          EntityLoader::convertToArticleReference);
      int numberOfMentions = countMentions(articleReferences);
      int averageScore = getAverage(articleReferences, numberOfMentions);
      return new EntityAndArticles(entity, averageScore, numberOfMentions, articleReferences);
    });
  }

  private static PreparableStatement createEntity(String entityValue) {
    return new PreparableStatement(ENTITY_QUERY, Lists.newArrayList(entityValue));
  }

  private static Entity convertToEntity(ResultSet rs) throws SQLException {
    String entityValue = rs.getString("entity_value");
    String entityRawValue = rs.getString("entity_value_raw");
    String entityType = rs.getString("entity_type");
    return new Entity(entityValue, entityRawValue, entityType);
  }

  private static PreparableStatement createArticleReferences(String entityValue) {
    return new PreparableStatement(ARTICLE_QUERY, Lists.newArrayList(entityValue));
  }

  private static ArticleReference convertToArticleReference(ResultSet rs) throws SQLException {
    long articleId = rs.getLong("article_id");
    long referenceId = rs.getLong("id");
    String articleTitle = rs.getString("title");
    EntityScore entityScore = Entities.readEntityValue(rs);
    return new ArticleReference(referenceId, articleId, articleTitle, entityScore);
  }

  private int countMentions(List<ArticleReference> articleReferences) {
    int num = 0;
    for (ArticleReference ref : articleReferences) {
      num += ref.getEntityScore().getNumberOfMentions();
    }
    return num;
  }

  private int getAverage(List<ArticleReference> articleReferences, int numberOfMentions) {
    double average = 0;
    for (ArticleReference ref : articleReferences) {
      double percentage = ref.getEntityScore().getNumberOfMentions() / (double)numberOfMentions;
      average += percentage * ref.getEntityScore().getAverageScore();
    }
    return (int)average;
  }

}
