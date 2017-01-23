package org.conjugates.analyzer.endpoints.info;

import org.conjugates.analyzer.services.properties.AnalyzerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/info")
@RestController
public class InfoController {

  private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);

  private final AnalyzerProperties properties;

  @Autowired
  public InfoController(AnalyzerProperties properties) {
    this.properties = properties;
  }

  @RequestMapping("/")
  @ResponseBody
  public InfoResponse info() {
    LOG.debug("Call to info made");
    return new InfoResponse(properties.getName(), properties.getVersion());
  }

}
