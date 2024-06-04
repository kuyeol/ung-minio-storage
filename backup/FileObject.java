package org.acme;

import io.minio.GetObjectArgs;

public class FileObject {

  private String fileName;
  private Long size;

  public FileObject() {
  }

  public static FileObject from(GetObjectArgs obj) {
    FileObject fo = new FileObject();
    if (obj != null) {
      fo.setFileName(obj.object());
      fo.setSize(obj.length());
    }
    return fo;
  }


  public String getFileName() {
    return fileName;
  }

  public FileObject setFileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public Long getSize() {
    return size;
  }

  public FileObject setSize(Long size) {
    this.size = size;
    return this;
  }
}
