package com.kamuri.telegram.model;

import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
public class Handler<T> {

  @NonNull String identifier;

  @NonNull Consumer<T> callback;
}
