package org.acme.extend;

import java.util.Collections;
import java.util.List;
import org.acme.User;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.connections.jpa.JpaConnectionProvider;


import org.keycloak.connections.jpa.JpaConnectionSpi;
public class UsersServiceProviderFactory implements JpaProvider {

  @Override
  public String getChangelogLocation() {
    return "META-INF/example-changelog.xml";
  }

  @Override
  public List<Class<?>> getEntities() {
    return Collections.<Class<?>>singletonList(User.class);
  }

  @Override
  public String getFactoryId() {
    return "getFactId";
  }

  @Override
  public void close() {

  }
}
