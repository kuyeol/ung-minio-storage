package org.acme.extend;

import java.util.List;
import org.keycloak.provider.Provider;

public interface JpaProvider extends Provider {

  String getChangelogLocation();

  List<Class<?>> getEntities();

  String getFactoryId();
}
