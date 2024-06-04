package org.acme.extend;

import java.util.logging.Logger;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.KeycloakSession;

public class SecretQuestionCredentialProvider implements CredentialProvider<SecretQuestionCredentialModel>,
  CredentialInputValidator {
  private static final Logger logger = Logger.getLogger(SecretQuestionCredentialProvider.class);

  protected KeycloakSession session;

  public SecretQuestionCredentialProvider(KeycloakSession session) {
    this.session = session;
  }
