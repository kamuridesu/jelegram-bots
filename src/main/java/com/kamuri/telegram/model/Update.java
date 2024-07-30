package com.kamuri.telegram.model;

import com.kamuri.telegram.model.update.Result;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Update {

  @NonNull private final Boolean ok;

  private final List<Result> result;
}
