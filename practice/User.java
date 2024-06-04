package org.acme.construct;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class User {

  private String name;
  private Set<User> friends = new HashSet<>();

  public User(String user) {
    this.name = user;
  }

  public String getName() {
    return name;
  }

  public void connectToFriend(User befriend) {
friends.add(befriend);
befriend.friends.add(this);
    System.out.println(this);

  }
public boolean isFriend(User befriend) {
    return friends.contains(befriend);
}


public Object[] List(){
    return friends.toArray();
}

  public Set<User> getFriends() {

    return friends;
  }



  public static void main(String[] arg)  {


    SeqBuild iO = new SeqBuild(1989);
    System.out.println(iO);

    User jane = new User("Jane");
    User jack = new User("Jack");
    User taylor = new User("taylor");

    jane.connectToFriend(jack);
    jack.connectToFriend(jane);

    System.out.println(jack.getName());
    System.out.println(jack.getFriends());
    System.out.println(jane.getFriends());


    System.out.println(jane.isFriend(jack));
    System.out.println(jane.isFriend(taylor));
  }
}
