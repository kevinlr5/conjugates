package org.sentiment.analyzer.services.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sentiment.analyzer.services.documents.Entity;
import org.sentiment.analyzer.services.documents.EntityScore;

public class Entities {

  private Entities() {
    // prevents construction
  }

  public static EntityScore readEntityValue(ResultSet rs) throws SQLException {
    String entityValue = rs.getString("entity_value");
    String entityRawValue = rs.getString("entity_value_raw");
    String entityType = rs.getString("entity_type");
    Entity entity = new Entity(entityValue, entityRawValue, entityType);
    int averageScore = rs.getInt("average_score");
    int numberOfMentions = rs.getInt("number_of_mentions");
    int weight = rs.getInt("weight");
    EntityScore score = new EntityScore(entity, averageScore, numberOfMentions, weight);
    return score;
  }

}
