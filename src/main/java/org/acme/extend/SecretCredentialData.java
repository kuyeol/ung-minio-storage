package org.acme.extend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretCredentialData {

  private final String question;

  @JsonCreator
  public SecretCredentialData (@JsonProperty("question") String question) {
    this.question = question;
  }

  public String getQuestion() {
    return question;
  }


}
