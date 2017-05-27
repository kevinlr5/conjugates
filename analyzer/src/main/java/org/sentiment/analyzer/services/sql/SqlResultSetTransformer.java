package org.sentiment.analyzer.services.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlResultSetTransformer<T> {

  T apply(ResultSet rs) throws SQLException;

}
