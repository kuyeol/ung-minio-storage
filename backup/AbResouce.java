package org.acme;

import io.minio.GetObjectArgs;
import io.minio.MinioAsyncClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import jakarta.inject.Inject;
import java.util.concurrent.CompletableFuture;

abstract public class AbResouce {
  private static final long PART_SIZE = 500 * 1024 * 1024;

  @Inject
  MinioAsyncClient minioClient;

  protected CompletableFuture<ObjectWriteResponse> buildPutFile(FormData formData) throws Exception {
    try {
      CompletableFuture<ObjectWriteResponse> pu =  minioClient.putObject(PutObjectArgs.builder()
          .bucket("admin")
          .object(formData.filename)
          .contentType(formData.mimetype)
          .stream(formData.getInputStream(), -1, PART_SIZE)
          .build());
      return pu;
    } catch (Exception e) {
      throw  new Exception(e);
    }
  }

  protected GetObjectArgs bulidGetFile(FormData formData) {

    return GetObjectArgs.builder().bucket("admin").object(formData.filename).build();
  }
}



