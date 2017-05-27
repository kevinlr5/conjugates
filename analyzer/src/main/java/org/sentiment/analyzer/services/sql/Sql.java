package org.sentiment.analyzer.services.sql;

import java.sql.SQLException;

public interface Sql<T> {

  T apply(SqlStatementService statementService) throws SQLException;

}
