package org.acme;



import io.minio.*;
import io.minio.messages.Item;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.reactive.RestForm;

import java.io.ByteArrayInputStream;

import java.io.File;
import java.util.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;







@Path("/api/user")
@ApplicationScoped
public class MinioResource {


    @Inject
    SecurityContext securityContext;


    private MinioClient minioClient;




    public MinioResource() {
        try {
            this.minioClient = MinioClient.builder().endpoint("http://182.218.135.229:9000").credentials("test_user", "test_secret").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GET
    public Response gettest() {
        Principal principal = securityContext.getUserPrincipal();
        return Response.ok(principal.getName()).build();
    }

    @GET
    @Path("aasa")
    public Response GETte() {
        Principal user = securityContext.getUserPrincipal();
        return Response.ok(user.equals(gettest())).build();
    }


    @GET
    @Path("/checkBucket")
    public Response checkBucket() {
        Principal user = securityContext.getUserPrincipal();

        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(user.getName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(user.getName()).build());
                return Response.status(Response.Status.CREATED).entity("Bucket created for user: " + user.getName()).build();
            } else {
                return Response.ok("Bucket already exists for user: " + user.getName()).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error occurred while checking bucket.").build();
        }
    }

    @GET
    @Path("UP")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public List<String> GETobj(String id) throws Exception, NoSuchAlgorithmException, InvalidKeyException {

        Principal user = securityContext.getUserPrincipal();

        Iterable<Result<Item>> obj       = minioClient.listObjects(ListObjectsArgs.builder().bucket(user.getName()).build());
        List<String>           objToList = new ArrayList<>();
        obj.forEach(o -> {


            try {

                objToList.add("Filename: " + o.get().objectName() + " | size:" + o.get().size() + "bytes");

            } catch (Exception e) {
                System.out.println("Error occurred: " + e);
            }
        });
        return objToList;

    }


    @GET
    @Path("/aaaa")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> DDD(String id) throws Exception {
        return GETobj(id);
    }


    @POST
    @Path("UPUP")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile()  throws Exception {

Principal user = securityContext.getUserPrincipal();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            builder.append(
                    "Sphinx of black quartz, judge my vow: Used by Adobe InDesign to display font samples. ");
            builder.append("(29 letters)\n");
            builder.append(
                    "Jackdaws love my big sphinx of quartz: Similarly, used by Windows XP for some fonts. ");
            builder.append("(31 letters)\n");
            builder.append(
                    "Pack my box with five dozen liquor jugs: According to Wikipedia, this one is used on ");
            builder.append("NASAs Space Shuttle. (32 letters)\n");
            builder.append(
                    "The quick onyx goblin jumps over the lazy dwarf: Flavor text from an Unhinged Magic Card. ");
            builder.append("(39 letters)\n");
            builder.append(
                    "How razorback-jumping frogs can level six piqued gymnasts!: Not going to win any brevity ");
            builder.append("awards at 49 letters long, but old-time Mac users may recognize it.\n");
            builder.append(
                    "Cozy lummox gives smart squid who asks for job pen: A 41-letter tester sentence for Mac ");
            builder.append("computers after System 7.\n");
            builder.append(
                    "A few others we like: Amazingly few discotheques provide jukeboxes; Now fax quiz Jack! my ");
            builder.append("brave ghost pled; Watch Jeopardy!, Alex Trebeks fun TV quiz game.\n");
            builder.append("---\n");
        }



        ByteArrayInputStream bais = new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));
        minioClient.putObject(
                PutObjectArgs.builder().bucket(user.getName()).object("a").stream(
                                bais, bais.available(), -1)
                        .build());
        bais.close();
        System.out.println("my-objectname is uploaded successfully");
        return Response.ok("File uploaded successfully").build();
    }


    @RestForm("file")
    public File data;



}

/*
생성자 문제 : 생성자가 MinioResource()발생할 수 있는 예외 목록을 선언하지만 비어 있습니다. 실제로 예외를 발생시키지 않는 한 생성자 선언에서 이러한 예외를 제거해야 합니다.
Duplicate Endpoint : @GET동일한 @Path("/h"). 이로 인해 충돌이 발생합니다. 이를 결합하거나 그 중 하나의 이름을 바꿔야 합니다.
사용되지 않는 변수 : String A="ADFASD";선언되었지만 사용되지 않았습니다. 코드를 깨끗하게 유지하기 위해 이를 제거할 수도 있습니다.
SecurityContext 주입SecurityContext : using 을 주입하려는 것 같지만 @Inject메서드에 제대로 주입되지 않습니다 onStart. @Context SecurityContext securityContext메소드 서명에서 매개변수를 제거 하고 삽입된 필드를 사용해야 합니다 this.securityContext.
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
