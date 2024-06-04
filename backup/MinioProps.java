package org.acme;


import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "minio.client")
public interface MinioProps {

    String url();
    String accessKey();
    String secretPass();

}
