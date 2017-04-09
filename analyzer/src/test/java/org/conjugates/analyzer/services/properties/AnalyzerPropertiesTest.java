package org.conjugates.analyzer.services.properties;

import org.conjugates.analyzer.framework.AnalyzerIntegrationBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AnalyzerPropertiesTest extends AnalyzerIntegrationBaseTest {

  @Autowired
  private AnalyzerProperties properties;

  @Test
  public void testName() {
    String name = properties.getName();
    Assert.assertEquals("Analyzer", name);
  }

  @Test
  public void testVersion() {
    String version = properties.getVersion();
    Assert.assertEquals("development", version);
  }

  @Test
  public void testCommitHash() {
    String commitHash = properties.getCommitHash();
    Assert.assertEquals("development", commitHash);
  }

  @Test
  public void testPort() {
    int port = properties.getPort();
    Assert.assertEquals(9090, port);
  }

}
