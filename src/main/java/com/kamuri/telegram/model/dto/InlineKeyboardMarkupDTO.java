package com.kamuri.telegram.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kamuri.telegram.model.dto.keyboard.InlineKeyboardButton;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class InlineKeyboardMarkupDTO {

  @NonNull
  @JsonProperty("inline_keyboard")
  private final InlineKeyboardButton[][] inlineKeyboardButtons;
}
