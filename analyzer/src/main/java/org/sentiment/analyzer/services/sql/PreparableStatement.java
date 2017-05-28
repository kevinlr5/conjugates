package org.sentiment.analyzer.services.sql;

import java.util.List;

public class PreparableStatement {

  private final String sql;
  private final List<Object> params;

  public PreparableStatement(String sql, List<Object> params) {
    this.sql = sql;
    this.params = params;
  }

  public String getSql() {
    return sql;
  }

  public List<Object> getParams() {
    return params;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((params == null) ? 0 : params.hashCode());
    result = prime * result + ((sql == null) ? 0 : sql.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PreparableStatement other = (PreparableStatement) obj;
    if (params == null) {
      if (other.params != null) {
        return false;
      }
    } else if (!params.equals(other.params)) {
      return false;
    }
    if (sql == null) {
      if (other.sql != null) {
        return false;
      }
    } else if (!sql.equals(other.sql)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "PreparableStatement [sql=" + sql + ", params=" + params + "]";
  }

}
