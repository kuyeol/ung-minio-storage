package org.acme.construct;

import java.util.Collection;
import java.util.HashSet;

import static java.lang.reflect.Array.set;

public class ConSet {
  private Collection<ConSet> group;

  private String element;
private int i;
  public ConSet() {

    group = new HashSet<ConSet>();
    group.add();
  }
public ConSet(String element) {
    this.element = element;
}
public void setI(int i) {
    this.i = i;
}
  public static void main(String[] ag){
    ConSet set = new ConSet();


    set.group.add();
    System.out.println(set.group);
  }

}
