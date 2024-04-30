package org.acme;



import io.minio.MinioClient;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class MinioConfig {

    public MinioClient minioClient(MinioProps properties){

        return MinioClient.builder()
                .endpoint(properties.url())
                .credentials(properties.accessKey(), properties.secretPass())
                .build();
    }

}
