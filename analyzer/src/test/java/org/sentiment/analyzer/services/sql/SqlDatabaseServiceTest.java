package org.sentiment.analyzer.services.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class SqlDatabaseServiceTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private SqlDatabaseService sql;

  @Test
  public void testSimpleQuery() throws SQLException {
    sql.runInTransaction(statementService -> {
      Statement statement = statementService.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT 1");
      resultSet.next();
      String result = resultSet.getString(1);
      Assert.assertEquals("1", result);
      return null;
    });
  }

  @Test
  public void testNestedTransactions() throws SQLException {
    Connection connection = sql.runInTransaction(statementService -> {
      Assert.assertFalse(statementService.getConnection().getAutoCommit());
      Connection ret = sql.runInTransaction(statementService2 -> {
        Assert.assertFalse(statementService2.getConnection().getAutoCommit());
        Assert.assertTrue(statementService.getConnection() == statementService2.getConnection());
        return statementService2.getConnection();
      });
      Assert.assertFalse(ret.isClosed());
      return ret;
    });
    Assert.assertTrue(connection.isClosed());

    sql.runInTransaction(statementService3 -> {
      Assert.assertFalse(statementService3.getConnection().getAutoCommit());
      Assert.assertTrue(connection != statementService3.getConnection());
      return null;
    });
  }

}
