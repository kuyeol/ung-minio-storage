package org.acme;

import io.minio.MinioAsyncClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entity.FormData;

@ApplicationScoped
public class MinioService {

  @Inject
  MinioAsyncClient  minioAsyncClient;

  @Inject
  MinioSdk minioSdk;



public Uni<FormData> upload(FormData formData) {
  return minioSdk.uploadObject(formData);
}




}
