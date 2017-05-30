package org.sentiment.analyzer.services.articles;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.sentiment.analyzer.services.documents.DocumentScore;
import org.sentiment.analyzer.services.documents.EntityScore;
import org.sentiment.analyzer.services.entity.Entities;
import org.sentiment.analyzer.services.sql.PreparableStatement;
import org.sentiment.analyzer.services.sql.SqlDatabaseService;
import org.springframework.stereotype.Service;

@Service
public class ArticleLoader {

  private static final String SELECT_DOCUMENT_SCORE_BY_ARTICLE =
      "SELECT * FROM document_score WHERE article_id = ?";
  private static final String SELECT_ARTICLE_BY_ID = "SELECT * FROM article WHERE id = ?";
  private static final String ENTITY_QUERY =
      "SELECT * "
      + " FROM entity_score "
      + " INNER JOIN entity ON entity_score.entity_value = entity.entity_value "
      + " WHERE entity_score.article_id = ? ";

  private final SqlDatabaseService sqlService;

  public ArticleLoader(SqlDatabaseService sqlService) {
    this.sqlService = sqlService;
  }

  public AnalyzedArticle load(long id) {
    return sqlService.runInTransaction(statementService -> {
      Iterable<EntityScoreAndDocumentScoreId> entityScores =
          statementService.query(
              createEntityScoreQuery(id),
              ArticleLoader::convertToEntityScore);
      Multimap<Long, EntityScore> scoresByDocumentScoreId = getScores(entityScores);
      List<DocumentScoreAndType> documentScores = statementService.query(
          createDocumentScoreQuery(id),
          rs -> convertToDocumentScore(rs, scoresByDocumentScoreId));
      List<AnalyzedArticle> article = statementService.query(
          createArticleQuery(id),
          rs -> convertToArticle(rs, documentScores));
      return Iterables.getOnlyElement(article);
    });
  }

  private static PreparableStatement createDocumentScoreQuery(long id) {
    return new PreparableStatement(SELECT_DOCUMENT_SCORE_BY_ARTICLE, Lists.newArrayList(id));
  }

  private static PreparableStatement createArticleQuery(long id) {
    return new PreparableStatement(SELECT_ARTICLE_BY_ID, Lists.newArrayList(id));
  }

  private static PreparableStatement createEntityScoreQuery(long id) {
    return new PreparableStatement(ENTITY_QUERY, Lists.newArrayList(id));
  }

  private static EntityScoreAndDocumentScoreId convertToEntityScore(ResultSet rs)
      throws SQLException {
    EntityScore score = Entities.readEntityValue(rs);
    long documentScoreId = rs.getLong("document_score_id");
    return new EntityScoreAndDocumentScoreId(documentScoreId, score);
  }

  private static DocumentScoreAndType convertToDocumentScore(
      ResultSet rs,
      Multimap<Long, EntityScore> entityScoresByDocumentScoreId) throws SQLException {
    long id = rs.getLong("id");
    int averageScore = rs.getInt("average_score");
    int weight = rs.getInt("weight");
    String type = rs.getString("type");
    DocumentScore score =
        new DocumentScore(entityScoresByDocumentScoreId.get(id), averageScore, weight);
    return new DocumentScoreAndType(type, score);
  }

  private static Multimap<Long, EntityScore> getScores(
      Iterable<EntityScoreAndDocumentScoreId> entityScores) {
    Multimap<Long, EntityScoreAndDocumentScoreId> entityScoresByDocumentScoreId =
        FluentIterable.from(entityScores)
        .index(EntityScoreAndDocumentScoreId::getDocumentScoreId);
    return Multimaps.transformValues(
        entityScoresByDocumentScoreId,
        EntityScoreAndDocumentScoreId::getEntityScore);
  }

  private AnalyzedArticle convertToArticle(
      ResultSet rs,
      List<DocumentScoreAndType> documentScores) throws SQLException {
    long id = rs.getLong("id");
    String title = rs.getString("title");
    DocumentScore titleScore = FluentIterable.from(documentScores)
        .firstMatch(ds -> ds.getType().equals("title"))
        .get()
        .getScore();
    DocumentScore bodyScore = FluentIterable.from(documentScores)
        .firstMatch(ds -> ds.getType().equals("body"))
        .get()
        .getScore();
    return new AnalyzedArticle(id, title, titleScore, bodyScore);
  }

  private static class EntityScoreAndDocumentScoreId {

    private final long documentScoreId;
    private final EntityScore entityScore;

    public EntityScoreAndDocumentScoreId(long documentScoreId, EntityScore entityScore) {
      this.documentScoreId = documentScoreId;
      this.entityScore = entityScore;
    }

    public long getDocumentScoreId() {
      return documentScoreId;
    }

    public EntityScore getEntityScore() {
      return entityScore;
    }

  }

  private static class DocumentScoreAndType {

    private final String type;
    private final DocumentScore score;

    public DocumentScoreAndType(String type, DocumentScore score) {
      this.type = type;
      this.score = score;
    }

    public String getType() {
      return type;
    }

    public DocumentScore getScore() {
      return score;
    }

  }

}
