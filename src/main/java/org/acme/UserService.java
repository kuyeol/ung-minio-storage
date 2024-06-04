package org.acme;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

  @Inject
  UserRepository userRepository;

  public User saveUser(User user) {
    userRepository.persist(user);
    return user;
  }

  public Uni<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
