package org.acme;

import io.minio.ListObjectsArgs;
import io.minio.MinioAsyncClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Path("/api/user")
@ApplicationScoped
public class MinioR extends AbResouce {

  private static final long PART_SIZE = 50 * 1024 * 1024;
  public String mimetype;
  public String filename;

  @Inject
  SecurityContext securityContext;

  @Inject
  Vertx vertx;
  private MinioAsyncClient minioClient;

  //    @GET
  //    @Path("/checkBucket")
  //    public CompletableFuture<Response> checkBucket() {
  //        Principal user = securityContext.getUserPrincipal();
  //
  //        try {
  //          minioClient.bucketExists(BucketExistsArgs.builder().bucket(user.getName()).build());
  //            if  {
  //                minioClient.makeBucket(MakeBucketArgs.builder().bucket(user.getName()).build());
  //                return Response.status(Response.Status.CREATED).entity("Bucket created for user: " + user
  //                .getName()).build();
  //            } else {
  //                return Response.ok("Bucket already exists for user: " + user.getName()).build();
  //            }
  //        } catch (Exception e) {
  //            e.printStackTrace();
  //            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred while checking
  //            bucket.").build();
  //        }
  //    }

  public MinioR() {
    try {
      this.minioClient = MinioAsyncClient.builder()
          .endpoint("http://182.218.135.229:9000")
          .credentials("test_user", "test_secret")
          .build();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @GET
  @Path("aasa")
  public Response GETte() {
    Principal user = securityContext.getUserPrincipal();
    return Response.ok(user.equals(gettest())).build();
  }

  @GET
  public Response gettest() {
    Principal principal = securityContext.getUserPrincipal();
    return Response.ok(principal.getName()).build();
  }

  public Response fileUp(String id, InputStream inputStream, String contentType) {
    Principal user = securityContext.getUserPrincipal();
    try {
      var obj = PutObjectArgs.builder()
          .object(id)
          .contentType(contentType)
          .stream(inputStream, -1, 10485760)
          .bucket(user.getName())
          .build();
      minioClient.putObject(obj);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Response.status(Response.Status.CREATED).entity("File up completed").build();
  }

  @GET
  @Path("/aaaa")
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> DDD(String id) throws Exception {
    return GETobj(id);
  }

  @GET
  @Path("UP")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public List<String> GETobj(String id) throws Exception {

    Principal user = securityContext.getUserPrincipal();

    Iterable<Result<Item>> obj = minioClient.listObjects(ListObjectsArgs.builder().bucket(user.getName()).build());

    List<String> objToList = new ArrayList<>();
    obj.forEach(o -> {
      try {
        objToList.add("Filename: " + o.get().objectName() + " | size:" + o.get().size() + "bytes");
      } catch (Exception e) {
        System.out.println("Error occurred: " + e);
      }
    });
    return objToList;
  }

  @POST
  @Path("UPUP")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.MULTIPART_FORM_DATA)

  public Uni<Response> uploadFile(FormData formData) throws Exception {
    Principal User = securityContext.getUserPrincipal();

    if (formData.filename == null || formData.filename.isEmpty()) {
      return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build());
    }

    if (formData.mimetype == null || formData.mimetype.isEmpty()) {
      return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build());
    }

    return Uni.createFrom().completionStage(() -> {

        return minioClient.putObject( buildPutFile(formData)   );

    }).onItem().ignore().andSwitchTo(Uni.createFrom().item(Response.created(null).build())).onFailure().recoverWithItem(th -> {
      th.printStackTrace();
      return Response.serverError().build();
    });
  }
    //return vertx.fileSystem().readFile(formData.data.getPath()).onItem()
    //    .transform(content -> Response.ok(content + "FINISH").build()).onItem().ignore()
    //    .andSwitchTo(Uni.createFrom().item(Response.created(null).build())).onFailure()
    //    .recoverWithItem(Response.status(Response.Status.BAD_REQUEST).build());
    //
  //  return Uni.createFrom().item(owr).onItem().transform(m -> Response.ok().build());


  //public Uni<Void> upLoad(String filename, String mimetype, InputStream inputStream,String buck) {
  //  return Uni.createFrom().completionStage(() -> { CompletableFuture.runAsync(()->{
  //    Principal User = securityContext.getUserPrincipal();
  //
  //    try {
  //      minioClient.putObject(PutObjectArgs.builder()
  //          .bucket(buck)
  //          .object(filename)
  //          .contentType(mimetype)
  //          .stream(inputStream, -1, PART_SIZE)
  //          .build()
  //
  //      );
  //    } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
  //      throw new RuntimeException("에라");
  //    }
  //  }))
  //}
  //}

  //@POST
  //@Path("2UPUP")
  //@Produces(MediaType.TEXT_PLAIN)
  //@Consumes(MediaType.MULTIPART_FORM_DATA)
  //public Response uploadEnd(FormData.java formData) {
  //  Principal User = securityContext.getUserPrincipal();
  //
  //  CompletableFuture<Void> up = upLoad(formData.filename, formData.mimetype, formData.inputStream, User.getName());
  //  up.thenRun(() -> {
  //
  //  }).exceptionally(ex->{
  //    throw new RuntimeException(ex);
  //  });
  //     return Response.accepted().build();
  //}

  //
  //    @POST
  //    @Path("UPUP")
  //    @Produces(MediaType.TEXT_PLAIN)
  //    @Consumes(MediaType.MULTIPART_FORM_DATA)
  //    public Uni<Response> uploadFile( FormData.java formData) throws Exception, IOException ,MinioException{
  //        Principal User = securityContext.getUserPrincipal();
  //
  //
  //
  //
  //        CompletableFuture
  //                <io.minio.ObjectWriteResponse> A=
  //              minioClient.uploadObject(UploadObjectArgs.builder().bucket(User.getName()).object(formData
  //              .filename).contentType(formData.mimetype).build());
  //
  //
  //
  //        return vertx.fileSystem().readFile(formData.filename)
  //                .onItem().transform(content->Response.ok(
  //                        content+"FINISH").build()).onFailure().recoverWithItem(Response.status(Response.Status
  //                        .BAD_REQUEST).build());
  //    }

  //
  //
  //    @POST
  //    @Path("UPU")
  //    public Uni<Response> uploadO(FormData.java formData) throws InsufficientDataException, IOException,
  //    NoSuchAlgorithmException, InvalidKeyException, XmlParserException, InternalException {
  //        Principal user = securityContext.getUserPrincipal();
  //
  //
  ////
  //        minioClient.uploadObject(UploadObjectArgs.builder().bucket(user.getName()).object(formData.filename)
  //        .contentType(formData.mimetype).build());
  //
  //        return vertx.fileSystem().readFile(formData.filename)
  //                .onItem().transform(content->Response.ok(
  //                        content+"FINISH").build()).onFailure().recoverWithItem(Response.status(Response.Status
  //                        .BAD_REQUEST).build());
  //
  //
  //
  //
  //    }

  //
  //    @POST
  //    @Path("UPUP")
  //    @Produces(MediaType.TEXT_PLAIN)
  //    @Consumes(MediaType.MULTIPART_FORM_DATA)
  //    public Uni<Response> reactUpload(FormData.java formData1)throws Exception{
  //
  //        return reactUpload(formData1).onItem().transform(item->Response.ok(item).status(201).build());
  //
  //    }
}

/*
생성자 문제 : 생성자가 MinioResource()발생할 수 있는 예외 목록을 선언하지만 비어 있습니다. 실제로 예외를 발생시키지 않는 한 생성자 선언에서 이러한 예외를 제거해야 합니다.
Duplicate Endpoint : @GET동일한 @Path("/h"). 이로 인해 충돌이 발생합니다. 이를 결합하거나 그 중 하나의 이름을 바꿔야 합니다.
사용되지 않는 변수 : String A="ADFASD";선언되었지만 사용되지 않았습니다. 코드를 깨끗하게 유지하기 위해 이를 제거할 수도 있습니다.
SecurityContext 주입SecurityContext : using 을 주입하려는 것 같지만 @Inject메서드에 제대로 주입되지 않습니다 onStart. @Context SecurityContext
securityContext메소드 서명에서 매개변수를 제거 하고 삽입된 필드를 사용해야 합니다 this.securityContext.
포착되지 않은 예외 : onStart메소드가 예외를 포착하지만 적절하게 처리하지 않습니다. 이를 기록하는 것은 좋지만 클라이언트에 의미 있는 응답을 반환하거나 최소한 관련 상태 코드를 반환하는 것도 고려해야 합니다.
리소스 관리 : 특히 스트림의 경우 적절한 리소스 관리를 보장합니다. 리소스 누출을 방지하려면 사용 후 스트림을 닫으세요.
끝점 이름 지정 : 끝점이 /api/user일반적인 것 같습니다. 상호 작용하는 리소스를 반영하는 의미 있는 엔드포인트 이름을 갖는 것이 좋습니다.










@Path("/api/minio")
@ApplicationScoped
public class MinioResource {

    @Inject
    SecurityContext securityContext;

    private MinioClient minioClient;

    public MinioResource() {
        try {
            this.minioClient = MinioClient.builder()
                .endpoint("http://182.218.135.229:9000")
                .credentials("test_user", "test_secret")
                .build();
        } catch (InvalidEndpointException | InvalidPortException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/checkBucket")
    public Response checkBucket() {
        Principal user = securityContext.getUserPrincipal();
        try {
            boolean found = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(user.getName()).build());
            if (!found) {
                minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(user.getName()).build());
                return Response.status(Response.Status.CREATED)
                    .entity("Bucket created for user: " + user.getName()).build();
            } else {
                return Response.ok("Bucket already exists for user: " + user.getName()).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error occurred while checking bucket.").build();
        }
    }

    // Other methods...

}
*/
