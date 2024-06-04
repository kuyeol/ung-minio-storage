package org.acme;

import io.minio.MinioAsyncClient;
import io.minio.PutObjectArgs;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.ext.auth.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import org.acme.entity.FormData;

public class MinioSdk implements  MinioBase{
@Inject
  SecurityContext securityContext;

  @Inject
  MinioAsyncClient minioAsyncClient;

  @Override
  public List<?> getList() {
    return List.of();
  }

  @Override
  public byte[] getObject(FormData formData) {
    return new byte[0];
  }

  @Override
  public void deleteObject(FormData data) {

  }

  @Override
  public Uni<FormData> uploadObject(FormData formData) {
Principal user = securityContext.getUserPrincipal();
    try{
      var obj=
          PutObjectArgs.builder().bucket("admin").object(formData.fileName).contentType(formData.fileType).bucket(
              formData.getBucketName()).stream(formData.getInputStream(),-1,10485760).build();
      minioAsyncClient.putObject(obj);
    }catch (Exception e){
      e.printStackTrace();
    }
   return uploadObject(formData);
  }
}
