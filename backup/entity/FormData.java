package org.acme.entity;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

public class FormData {

  @Inject
  User user;

  @RestForm("file")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  public File file;

  @RestForm
  @PartType(MediaType.TEXT_PLAIN)
  public String fileName;

  @RestForm
  @PartType(MediaType.TEXT_PLAIN)
  public String fileType;

  @RestForm
  @PartType(MediaType.TEXT_PLAIN)
  private String bucketName;

  public String getBucketName() {
    this.bucketName = user.userName;
    return this.bucketName;
  }

  public InputStream getInputStream() throws FileNotFoundException {
    return new FileInputStream(file);
  }
}
