package com.kamuri.telegram.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class SendMessageDTO {

  @NonNull
  @JsonProperty("chat_id")
  private final String chatId;

  @NonNull private final String text;

  @JsonProperty("parse_mode")
  private final String parseMode = "html";

  @JsonProperty("reply_markup")
  InlineKeyboardMarkupDTO inlineKeyboardMarkup;
}
