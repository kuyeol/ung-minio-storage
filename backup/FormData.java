package org.acme;

import jakarta.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

public class FormData {

  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  @RestForm("file")
  public File data;

  @RestForm
  @PartType(MediaType.TEXT_PLAIN)
  public String filename;

  @RestForm
  @PartType(MediaType.TEXT_PLAIN)
  public String mimetype;

  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  @RestForm("file")
  InputStream inputStream;



  public InputStream getInputStream() throws IOException {
inputStream = new ByteArrayInputStream(inputStream.readAllBytes());

    return inputStream;
  }

}
