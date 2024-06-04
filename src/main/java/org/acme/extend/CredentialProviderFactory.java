package org.acme.extend;





public class CredentialProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider> {

  public static final String PROVIDER_ID =  "secret-question";

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public CredentialProvider create(KeycloakSession session) {
    return new SecretQuestionCredentialProvider(session);
  }
}
