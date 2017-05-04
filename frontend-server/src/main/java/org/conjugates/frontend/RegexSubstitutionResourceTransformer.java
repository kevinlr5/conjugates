package org.conjugates.frontend;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.resource.ResourceTransformer;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

public class RegexSubstitutionResourceTransformer implements ResourceTransformer {

  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  private final String filename;
  private final String regex;
  private final String replacement;

  public RegexSubstitutionResourceTransformer(String filename, String regex, String replacement) {
    this.filename = filename;
    this.regex = regex;
    this.replacement = replacement;
  }

  @Override
  public Resource transform(
      HttpServletRequest request,
      Resource resource,
      ResourceTransformerChain transformerChain) throws IOException {
    Resource transformed = transformerChain.transform(request, resource);
    if (!filename.equals(transformed.getFilename())) {
      return transformed;
    } else {
      byte[] bytes = FileCopyUtils.copyToByteArray(transformed.getInputStream());
      String content = new String(bytes, DEFAULT_CHARSET);
      String withUrl = content.replaceAll(regex, replacement);
      return new TransformedResource(transformed, withUrl.getBytes(DEFAULT_CHARSET));
    }
  }



}
