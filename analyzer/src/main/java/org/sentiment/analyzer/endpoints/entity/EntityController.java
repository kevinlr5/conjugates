package org.sentiment.analyzer.endpoints.entity;

import org.sentiment.analyzer.services.entity.EntityAndArticles;
import org.sentiment.analyzer.services.entity.EntityLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/entity")
@RestController
public class EntityController {


  private final EntityLoader entityLoader;

  @Autowired
  public EntityController(EntityLoader entityLoader) {
    this.entityLoader = entityLoader;
  }

  @RequestMapping(value = "/{value}", method = RequestMethod.GET)
  @ResponseBody
  public EntityAndArticles get(@PathVariable("value") String entityValue) {
    return entityLoader.get(entityValue);
  }

}
