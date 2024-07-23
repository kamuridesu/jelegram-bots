package com.kamuri.telegram.model.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kamuri.telegram.model.message.Message;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Result {

    @JsonProperty("update_id")
    private final Integer updateId;

    Message message;

    @JsonProperty(value = "callback_query")
    private final CallbackQuery callbackQuery;

}
