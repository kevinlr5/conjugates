package org.sentiment.analyzer.services.sql;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlStatementService {

  private final Connection conn;

  public SqlStatementService(Connection conn) {
    this.conn = conn;
  }

  public int execute(PreparableStatement preparableStatement) throws SQLException {
    try (PreparedStatement preparedStatement =
        conn.prepareStatement(preparableStatement.getSql())) {
      validateParameterCount(preparableStatement, preparedStatement);
      setObjects(preparedStatement, preparableStatement.getParams());
      Preconditions.checkArgument(!preparedStatement.execute());
      return preparedStatement.getUpdateCount();
    }
  }

  public long insertAndGetId(PreparableStatement preparableStatement) throws SQLException {
    try (PreparedStatement preparedStatement =
        conn.prepareStatement(preparableStatement.getSql(), Statement.RETURN_GENERATED_KEYS)) {
      validateParameterCount(preparableStatement, preparedStatement);
      setObjects(preparedStatement, preparableStatement.getParams());
      Preconditions.checkArgument(preparedStatement.executeUpdate() == 1);
      ResultSet rs = preparedStatement.getGeneratedKeys();
      rs.next();
      return rs.getLong(1);
    }
  }

  public <T> List<T> query(PreparableStatement preparableStatement, SqlResultSetTransformer<T> fn)
      throws SQLException {
    try (PreparedStatement preparedStatement =
        conn.prepareStatement(preparableStatement.getSql())) {
      validateParameterCount(preparableStatement, preparedStatement);
      setObjects(preparedStatement, preparableStatement.getParams());
      executePreparedStatement(preparedStatement);
      return ResultSets.transform(preparedStatement, fn);
    }
  }

  public int insertBatch(PreparableBatchStatement preparableStatement) throws SQLException {
    try (PreparedStatement preparedStatement =
        conn.prepareStatement(preparableStatement.getSql())) {
      validateBatchParameterCount(preparableStatement, preparedStatement);
      for (List<Object> params : preparableStatement.getParams()) {
        setObjects(preparedStatement, params);
        preparedStatement.addBatch();
      }
      int[] insertCounts = preparedStatement.executeBatch();
      return sumInsertCounts(insertCounts);
    }
  }

  public <T> List<List<T>> queryBatch(
      PreparableBatchStatement preparableStatement,
      SqlResultSetTransformer<T> fn) throws SQLException {
    List<List<T>> results = Lists.newArrayList();
    try (PreparedStatement preparedStatement =
        conn.prepareStatement(preparableStatement.getSql())) {
      validateBatchParameterCount(preparableStatement, preparedStatement);
      for (List<Object> params : preparableStatement.getParams()) {
        setObjects(preparedStatement, params);
        executePreparedStatement(preparedStatement);
        results.add(ResultSets.transform(preparedStatement, fn));
      }
      return results;
    }
  }

  @VisibleForTesting
  public Connection getConnection() {
    return conn;
  }

  private static void validateBatchParameterCount(PreparableBatchStatement preparableStatement,
      PreparedStatement preparedStatement) throws SQLException {
    int expectedParamSize = preparedStatement.getParameterMetaData().getParameterCount();
    for (List<Object> params : preparableStatement.getParams()) {
      if (params.size() != expectedParamSize) {
        throw new IllegalArgumentException("Wrong number of parameters: expected "
            + expectedParamSize + ", was given " + params.size() + ", values: " + params
            + ", for sql: " + preparableStatement.getSql());
      }
    }
  }

  private static int sumInsertCounts(int[] insertCounts) {
    int insertCount = 0;
    for (int i = 0; i < insertCounts.length; i++) {
      insertCount += insertCounts[i];
    }
    return insertCount;
  }

  private static void validateParameterCount(PreparableStatement preparableStatement,
      PreparedStatement preparedStatement) throws SQLException {
    int expectedParamSize = preparedStatement.getParameterMetaData().getParameterCount();
    List<Object> params = preparableStatement.getParams();
    if (params.size() != expectedParamSize) {
      throw new IllegalArgumentException("Wrong number of parameters: expected " + expectedParamSize
          + ", was given " + params.size() + ", values: " + params + ", for sql: "
          + preparableStatement.getSql());
    }
  }

  private static void setObjects(PreparedStatement preparedStatement, List<Object> params)
      throws SQLException {
    int paramIndex = 1;
    for (Object object : params) {
      preparedStatement.setObject(paramIndex, object);
      paramIndex += 1;
    }
  }

  private static void executePreparedStatement(PreparedStatement preparedStatement) {
    try {
      Preconditions.checkArgument(preparedStatement.execute());
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }
}
