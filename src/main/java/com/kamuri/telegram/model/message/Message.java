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
public class Message {

    @JsonProperty("message_id")
    private final long messageId;

    private final From from;

    private final Chat chat;

    private final long date;

    @JsonProperty("edit_date")
    private final long editDate;

    private final String text;

}
