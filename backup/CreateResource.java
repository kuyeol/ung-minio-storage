package org.acme;

import io.minio.MinioAsyncClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;


@Path("new")
@ApplicationScoped
public class CreateResource extends AbResouce {

//  @Inject
//  MinioAsyncClient minioAsyncClient;
//
//
//
//  public CreateResource() {
//  }
//
//  @POST
//  @Path("AsyncUp")
//  @Consumes(MediaType.MULTIPART_FORM_DATA)
//  public Uni<Response> upload(FormData.java formData) throws IOException {
//return
//    Uni.createFrom().completionStage(() -> {
//      try {
//        return minioAsyncClient.putObject(buildPutFile(formData));
//      }catch (Exception e){
//        throw new RuntimeException(e);
//      }
//
//    }).onItem().ignore().andSwitchTo(Uni.createFrom().item(Response.created(null).build())).onFailure().recoverWithItem(th->{
//      th.printStackTrace();
//      return Response.serverError().build();
//    });
//  }




}
