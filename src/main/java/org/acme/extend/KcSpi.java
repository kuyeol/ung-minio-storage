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
    //서비스명 users
    return "";
  }

  @Override
  public Class<? extends Provider> getProviderClass() {

    //서비스 클래스 반환 UsersService.class
    return UsersService.class;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public Class<? extends ProviderFactory> getProviderFactoryClass() {

    /*
    서비스프로바이더팩토리 클래스 반환
    UsersServiceProviderFactory.class
    */


    return  UsersServiceProviderFactory.class;
  }
}
