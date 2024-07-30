package com.kamuri.telegram.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class From {

  private final long id;

  @JsonProperty("is_bot")
  private final boolean isBot;

  @JsonProperty("first_name")
  private final String firstName;

  private final String username;
}
