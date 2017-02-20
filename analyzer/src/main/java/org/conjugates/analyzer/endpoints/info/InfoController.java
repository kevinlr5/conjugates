package org.conjugates.analyzer.endpoints.info;

import org.conjugates.analyzer.server.exception.UnauthorizedException;
import org.conjugates.analyzer.services.properties.AnalyzerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/info")
@RestController
public class InfoController {

  private static final Logger LOG = LoggerFactory.getLogger(InfoController.class);

  private final AnalyzerProperties properties;

  @Autowired
  public InfoController(AnalyzerProperties properties) {
    this.properties = properties;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  @ResponseBody
  public InfoResponse info() {
    LOG.debug("Call to info made");
    return new InfoResponse(properties.getName(), properties.getVersion());
  }

  @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
  @ResponseBody
  public InfoResponse unauthorized() {
    throw new UnauthorizedException("unauthorized!!!");
  }

  @RequestMapping(value = "/testpost", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public void testPost(@RequestBody InfoTestPostRequest request) {
    LOG.debug("POST!!!!" + request.getValue());
  }

}
