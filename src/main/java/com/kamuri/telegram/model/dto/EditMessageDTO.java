package com.kamuri.telegram.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class EditMessageDTO {

  @NonNull
  @JsonProperty("chat_id")
  private final String chatId;

  @NonNull
  @JsonProperty("message_id")
  private final Long messageId;

  @NonNull private final String text;

  @NonNull
  @JsonProperty("parse_mode")
  private final String parseMode = "html";
}
