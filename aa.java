import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/upload")
public class FileUploadResource {

    private static final String MINIO_ENDPOINT = "http://localhost:9000";
    private static final String MINIO_ACCESS_KEY = "minioadmin";
    private static final String MINIO_SECRET_KEY = "minioadmin";
    private static final String MINIO_BUCKET_NAME = "mybucket";

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadFile(@MultipartForm MultipartBody data) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(MINIO_ENDPOINT)
                    .credentials(MINIO_ACCESS_KEY, MINIO_SECRET_KEY)
                    .build();

            // Check if the bucket exists, create it if not
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(MINIO_BUCKET_NAME).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(MINIO_BUCKET_NAME).build());
            }

            // Upload the file to MinIO
            InputStream fileInputStream = data.file;
            long size = data.file.size();
            String fileName = data.fileName;
            String contentType = data.file.contentType();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(MINIO_BUCKET_NAME)
                            .object(fileName)
                            .stream(fileInputStream, size, -1)
                            .contentType(contentType)
                            .build());

            return Response.ok("File uploaded successfully").build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    public static class MultipartBody {
        @RestForm
        @PartType(MediaType.APPLICATION_OCTET_STREAM)
        public InputStream file;

        @RestForm("fileName")
        @PartType(MediaType.TEXT_PLAIN)
        public String fileName;
    }
