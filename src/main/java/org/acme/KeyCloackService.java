package org.acme;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

public class KeyCloackService {




  @Inject
  SecurityContext securityContext;

  @Inject
  SecurityIdentity securityIdentity;

//  public void getKey(){
//securityIdentity.
//  }


}
