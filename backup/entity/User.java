package org.acme.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_entity")
public class User extends PanacheEntity {


  public String email;
  public String userName;
  public String password;




}
