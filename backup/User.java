package org.acme;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    public int values;

    @Column(unique = true, nullable = false)
    public String       name;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    public List<String> roles;
    public String       password;
    public String       dePass;


    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }



}
