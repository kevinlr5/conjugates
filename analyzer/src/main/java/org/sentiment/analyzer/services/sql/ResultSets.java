package org.sentiment.analyzer.services.sql;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ResultSets {

  private ResultSets() {
    // Utility
  }

  public static <T> List<T> transform(Statement stmt, SqlResultSetTransformer<T> fn) {
    List<T> ret = Lists.newArrayList();
    try (ResultSet rs = stmt.getResultSet()) {
      while (rs.next()) {
        ret.add(fn.apply(rs));
      }
      return ret;
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  /*
   * Contends with a weird sql quirk
   */
  public static Optional<Long> getOptionalLong(ResultSet rs, String columnName)
      throws SQLException {
    long value = rs.getLong(columnName);
    if (rs.wasNull()) {
      return Optional.absent();
    } else {
      return Optional.of(value);
    }
  }

}
