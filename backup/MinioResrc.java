package org.acme;

import io.minio.MinioAsyncClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;
import org.acme.entity.FormData;

@Path("new")
@ApplicationScoped
public class MinioResrc {


@Inject
  MinioAsyncClient minioAsyncClient;
@Inject
  MinioService minioService;
@Inject
  SecurityContext securityContext;
  @POST
  @Path("UP")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Uni<Response> upload(FormData formData) {
Principal user = securityContext.getUserPrincipal();

  return minioService.upload(formData).onItem().transform(item->Response.ok().entity(item).build());
}


}
