package org.sentiment.analyzer.framework;

import org.junit.runner.RunWith;
import org.sentiment.analyzer.AnalyzerApplication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AnalyzerApplication.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AnalyzerIntegrationBaseTest {

  public static final String ASCIIDOCTOR_SNIPPETS_DIR = "build/generated-snippets";

}
