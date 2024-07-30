package com.kamuri.telegram.model.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class MessageDTO {

  private final Integer offset;

  private final Integer timeout = 10;
}
