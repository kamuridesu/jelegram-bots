package com.kamuri.telegram.util;

import static com.kamuri.telegram.util.StringUtils.isEmptyOrNull;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Util methods for messages.
 *
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
   *
   * @param message string to be masked
   * @return message with masked token
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  public String maskToken(String message) {
    return isEmptyOrNull(message) ? message : message.replace(token, "TOKEN");
  }
}
