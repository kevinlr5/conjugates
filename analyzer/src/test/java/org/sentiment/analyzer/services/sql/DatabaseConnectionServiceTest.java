package org.sentiment.analyzer.services.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;
import org.sentiment.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseConnectionServiceTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private DatabaseConnectionService connectionService;

  @Test
  public void testGetConnection() throws SQLException {
    Connection conn = connectionService.getConnection();
    conn.close();
  }

}
