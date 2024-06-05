package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Hello {
  public String sayHello() {
    return "Hello World!";
  }
}
