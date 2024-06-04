package org.acme;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Response;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.util.Base64Url;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.RealmRepresentation;

public class KeyConfig {

  @ConfigProperty(name = "quarkus.oidc.end-session-endpoint")
  String keycloakEndSessionEndpoint;

  @GET
  @Authenticated
  public Response logout() {
    return Response.status(302)
        .header("Location", keycloakEndSessionEndpoint + "?redirect_uri=http://localhost:8080")
        .build();
  }


  Keycloak keycloak = Keycloak.getInstance(
      "http://localhost:8080",
      "master",
      "admin",
      "password",
      "admin-cli");

  RealmRepresentation realm = keycloak.realm("master").toRepresentation();

  KeycloakSecurityContext session = (KeycloakSecurityContext) httpServletRequest.getAttribute(KeycloakSecurityContext.class.getName());
  AccessToken token = session.getToken();
  String clientId = token.getIssuedFor();
  String nonce = UUID.randomUUID().toString();
  MessageDigest md = null;
   try {
    md = MessageDigest.getInstance("SHA-256");
  } catch (
  NoSuchAlgorithmException e) {
    throw new RuntimeException(e);
  }
  String input = nonce + token.getSessionState() + clientId + provider;
  byte[] check = md.digest(input.getBytes(StandardCharsets.UTF_8));
  String hash = Base64Url.encode(check);
   request.getSession().setAttribute("hash", hash);
  String redirectUri = ...;
  String accountLinkUrl = KeycloakUriBuilder.fromUri(authServerRootUrl)
      .path("/realms/{realm}/broker/{provider}/link")
      .queryParam("nonce", nonce)
      .queryParam("hash", hash)
      .queryParam("client_id", clientId)
      .queryParam("redirect_uri", redirectUri).build(realm, provider).toString();


}


