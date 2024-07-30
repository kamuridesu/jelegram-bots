package com.kamuri.telegram.model;

import java.util.function.Consumer;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Handler<T> {

  @NonNull String identifier;

  @NonNull Consumer<T> callback;
}
