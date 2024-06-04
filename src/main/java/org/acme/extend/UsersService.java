package org.acme.extend;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.provider.Provider;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public class UsersService {

  UserRepresentation user = new UserRepresentation();
Keycloak keycloak;
  RealmResource realmResource = keycloak.realm("realm");
  UsersResource usersResource = realmResource.users();
  Response response = usersResource.create(user);

}
