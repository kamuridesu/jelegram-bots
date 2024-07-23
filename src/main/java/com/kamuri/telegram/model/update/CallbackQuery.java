package com.kamuri.telegram.model.update;

import com.kamuri.telegram.model.message.Message;

import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class CallbackQuery {

    @NonNull
    private final String id;

    @NonNull
    private final User from;

    @NonNull
    private final Message message;

    private final String data;

}
