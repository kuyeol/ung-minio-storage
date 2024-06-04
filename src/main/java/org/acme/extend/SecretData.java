package org.acme.extend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretData {


  private final String answer;

  @JsonCreator
  public SecretData (@JsonProperty("answer") String answer) {
    this.answer = answer;
  }

  public String getAnswer() {
    return answer;
  }

}
