package org.conjugates.analyzer.endpoints.info;

import org.conjugates.analyzer.services.properties.AnalyzerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/info")
@RestController
public class InfoController {

  private final AnalyzerProperties properties;

  @Autowired
  public InfoController(AnalyzerProperties properties) {
    this.properties = properties;
  }

  @RequestMapping("/")
  public String name() {
    return properties.getName();
  }

}
