package com.kamuri.telegram.model;

import com.kamuri.telegram.model.message.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class MessageUpdate {

    private final boolean ok;

    private final Message result;

}
