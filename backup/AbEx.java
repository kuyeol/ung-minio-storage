package org.acme;


import io.minio.MinioClient;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.io.FileInputStream;
import java.io.InputStream;

import static io.smallrye.mutiny.helpers.spies.Spy.onItem;

@Path("new")
public class AbEx extends AbResouce {
    @Inject
    MinioClient minioClient;

}
