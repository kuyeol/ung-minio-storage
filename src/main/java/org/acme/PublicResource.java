package org.acme;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.account.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;

@Path("")
public class PublicResource {

  private static final Logger LOG = Logger.getLogger(PublicResource.class);

  @Inject
  UserService userService;

  @Inject
  SecurityIdentity securityIdentity;

  @Inject
  JsonWebToken jwt;
  @Inject
  SecurityContext securityContext;
  @ConfigProperty(name = "quarkus.oidc.auth-server-url")
  String authServerUrl;
  @ConfigProperty(name = "quarkus.oidc.client-id")
  String clientId;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("user")
  public User getUserInfo() {
    String username = securityIdentity.getPrincipal().getName();
    User user = (User) userService.findByUsername(username);

    if (user == null) {
      user = new User();
      user.username = username;
      user.email = jwt.getClaim("email");
      userService.saveUser(user);
    }

    return user;
  }

  //@GET
  //@Path("/logout")
  //public String logout(OidcTenantConfig.Authentication authentication) {
  //  if (authentication != null) {
  //    // Perform any necessary logout operations here
  //  }
  //  return "aa";
  //}
  //@Inject
  //UriInfo uriInfo;

  @Path("api/HOME")
  @GET
  public String HI() {

    return "hi" + securityContext.getUserPrincipal().getName();
  }

  @Path("/")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.TEXT_PLAIN)
  public String Home() {
    return "home";
  }

  public Keycloak keyCloak() {
    return KeycloakBuilder.builder()
        .serverUrl("http://182.218.135.229:7777/realms/quarkus")
        .realm("quarkus")
        .clientId("users-service")
        .clientSecret("secret")
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .username("quarkus")
        .password("quarkus")
        .build();
  }


  @Inject
  KeyConfig keyConfig;



  public Response kc(){
    return Response.ok(keyConfig.getKC()).build();
  }





@Inject
Keycloak keycloak;

//public String createU(String name,String fName, String lName,String password,String email) {
//
//  RealmResource realm = keyCloak().realm("quarkus");
//    UserRepresentation user = new UserRepresentation();
//    user.setUsername(name);
//    user.setFirstName(email);
//    user.setFirstName(fName);
//    user.setLastName(lName);
//    Response res= realm.users().create();
//if(res.getStatus()==201){
//  String userId =res.getLocation().getPath().replace(".*/([^/]+)$", "$1");
//  CredentialRepresentation credential = new CredentialRepresentation();
//  credential.setTemporary(false);
//  credential.setType(CredentialRepresentation.PASSWORD);
//  credential.setValue(password);
//
//  realm.users().get(userId).resetPassword(credential);
//
//  return userId;
// }else {
//  throw new RuntimeException("FAIL"+res.getStatus());
// }
//
//}

  @GET
  @Path("logout")
  public Response logout() {
    String redirectUri = "http://localhost:8080/HOME";  // 클라이언트 URL
    String logoutUrl = UriBuilder.fromUri(authServerUrl)
        .path("/protocol/openid-connect/logout")
        .queryParam("client_id", clientId)
        .queryParam("post_logout_redirect_uri", redirectUri)
        .build()
        .toString();

    return Response.status(302).header("Location", logoutUrl).build();
  }
}
