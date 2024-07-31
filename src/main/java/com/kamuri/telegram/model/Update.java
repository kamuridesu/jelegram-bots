package com.kamuri.telegram.model;

import com.kamuri.telegram.model.update.Result;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Getter
@Builder
@RequiredArgsConstructor
public class Update {

  @NonNull private final Boolean ok;

  @Builder.Default private final List<Result> result = Collections.<Result>emptyList();
}
