package com.kamuri.telegram.model.dto.keyboard;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class InlineKeyboardButton {

    @NonNull
    private final String text;

    @JsonProperty("callback_data")
    private final String callbackData;

}
