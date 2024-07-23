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
public class Chat {
    
    private final long id;

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    private final String username;

    private final String type;
    
}
