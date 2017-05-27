package org.sentiment.analyzer.services.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.sentiment.analyzer.services.properties.AnalyzerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlConnectionService implements DatabaseConnectionService {

  private static final String POOL = "pool-analzyer";

  private final HikariDataSource ds;

  @Autowired
  public MysqlConnectionService(AnalyzerProperties properties) throws ClassNotFoundException {
    // https://dev.mysql.com/doc/connector-j/
    // 5.1/en/connector-j-usagenotes-connect-drivermanager.html
    Class.forName("com.mysql.jdbc.Driver");

    String hostname = properties.getDbHostname();
    String user = properties.getDbUser();
    String passord = properties.getDbPassword();
    String schema = properties.getDbSchema();

    // https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby
    // MysqlDataSource is known to be broken, use jdbcUrl configuration instead.
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://" + hostname + "/" + schema);
    config.setUsername(user);
    config.setPassword(passord);
    config.setPoolName(POOL);
    config.setConnectionTimeout(30000);
    config.setMinimumIdle(1);
    config.setMaximumPoolSize(10);
    config.setIdleTimeout(600000);
    ds = new HikariDataSource(config);
  }

  @Override
  public Connection getConnection() throws SQLException {
    return ds.getConnection();
  }

  @Override
  @PreDestroy
  public void close() {
    ds.close();
  }

}
