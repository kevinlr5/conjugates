package org.sentiment.analyzer.services.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectionService {

  Connection getConnection() throws SQLException;

  void close();

}
