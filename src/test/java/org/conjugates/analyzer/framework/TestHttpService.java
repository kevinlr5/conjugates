package org.conjugates.analyzer.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Service
public class TestHttpService {

  private final WebApplicationContext webApplicationContext;

  @Autowired
  public TestHttpService(WebApplicationContext webApplicationContext) {
    this.webApplicationContext = webApplicationContext;
  }

  public MockMvc mvc() {
    return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

}
