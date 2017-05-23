package org.sentiment.analyzer.endpoints.document;

import org.sentiment.analyzer.services.documents.AnalyzedDocument;
import org.sentiment.analyzer.services.documents.DocumentAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/document")
@RestController
public class DocumentController {

  private final DocumentAnalyzer documentAnalyzer;

  @Autowired
  public DocumentController(DocumentAnalyzer documentAnalyzer) {
    this.documentAnalyzer = documentAnalyzer;
  }

  @RequestMapping(value = "/analyze", method = RequestMethod.POST)
  @ResponseBody
  public AnalyzedDocumentResponse analyze(@RequestBody AnalyzedDocumentRequest request) {
    String text = request.getText();
    AnalyzedDocument analyzedDocument = documentAnalyzer.analyze(text);
    return AnalyzedDocumentResponse.from(analyzedDocument);
  }

}
