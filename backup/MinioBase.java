package org.acme;

import io.smallrye.mutiny.Uni;
import java.util.List;
import org.acme.entity.FormData;

public interface MinioBase {

  List<?> getList();

  byte[] getObject(FormData formData);

  void deleteObject(FormData formData);

  Uni<FormData> uploadObject(FormData formData);
}
