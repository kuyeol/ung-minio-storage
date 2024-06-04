package org.acme.extend;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class KcSpi implements Spi {

  @Override
  public boolean isInternal() {
    return false;
  }

  @Override
  public String getName() {
    //서비스명
    return "";
  }

  @Override
  public Class<? extends Provider> getProviderClass() {

    //
    return null;
  }

  @Override
  public Class<? extends ProviderFactory> getProviderFactoryClass() {
    return null;
  }
}
