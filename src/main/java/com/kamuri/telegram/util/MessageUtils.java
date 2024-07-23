package com.kamuri.telegram.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Util methods for messages.
 * @author Kamuri Amorim
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@RequiredArgsConstructor
public class MessageUtils {

    @Value("${spring.telegram.BOT_TOKEN}")
    private String token;

    /**
     * Mask token in strings for output
     * @param message string to be masked
     * @return message with masked token
     * @author Kamuri Amorim
     * @version 0.0.1
     * @since 0.0.1
     */
    public String maskToken(String message) {
        return token == "" || token == null ? message : message.replace(token, "TOKEN");
    }
}
