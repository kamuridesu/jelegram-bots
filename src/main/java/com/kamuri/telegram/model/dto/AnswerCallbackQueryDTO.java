package com.kamuri.telegram.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class AnswerCallbackQueryDTO {

    @NonNull
    @JsonProperty("callback_query_id")
    private final String callbackQueryID;

    String text;

    @Builder.Default
    @JsonProperty("show_alert")
    private final Boolean showAlert = false;
    
}
