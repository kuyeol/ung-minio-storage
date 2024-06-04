package org.acme.construct;

public class SeqBuild implements Seq {

  private int num;

  public SeqBuild(int n) {
    this.num = n;
  }

  public boolean hasNext() {
    return num != 0;
  }

  public int next() {
    int rs = num % 10;
    num /= 10;
    return rs;
  }

public int rest(){
    return num;
}

}
