package org.sentiment.analyzer.services.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqlDatabaseService {

  private final DatabaseConnectionService connService;
  private final ThreadLocal<Connection> connectionInTransaction = new ThreadLocal<>();

  @Autowired
  public SqlDatabaseService(DatabaseConnectionService connService) {
    this.connService = connService;
  }

  public <T> T runInTransaction(Sql<T> transaction) {
    boolean isNewConnection = false;
    Connection cnx = null;
    try {
      cnx = getCurrentConnection();
      isNewConnection = cnx.getAutoCommit();
      if (isNewConnection) {
        cnx.setAutoCommit(false);
      }
      return executeStatement(transaction, cnx, isNewConnection);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    } finally {
      if (isNewConnection) {
        connectionInTransaction.set(null);
        if (cnx != null) {
          try {
            cnx.close();
          } catch (SQLException ex) {
            // close failed
          }
        }
      }
    }
  }

  private Connection getCurrentConnection() throws SQLException {
    Connection cnx = connectionInTransaction.get();
    if (cnx != null) {
      return cnx;
    } else {
      Connection newConnection = connService.getConnection();
      connectionInTransaction.set(newConnection);
      return newConnection;
    }
  }

  private static <T> T executeStatement(
      Sql<T> transaction,
      Connection conn,
      boolean isNewConnection) {
    try {
      T ret = transaction.apply(new SqlStatementService(conn));
      if (isNewConnection) {
        conn.commit();
      }
      return ret;
    } catch (Throwable th) {
      try {
        conn.rollback();
      } catch (SQLException ex) {
        // rollback failed
      }
      throw new RuntimeException(th);
    }
  }


}
