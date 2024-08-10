package com.kamuri.telegram.util;

import static com.kamuri.telegram.util.StringUtils.isEmptyOrNull;

import lombok.experimental.UtilityClass;

/**
 * Util methods for messages.
 *
 * @author Kamuri Amorim
 * @version 0.0.1
 * @since 0.0.1
 */
@UtilityClass
public class MessageUtils {

  private static final String TOKEN_MASK = "TOKEN";

  /**
   * Mask token in strings for output
   *
   * @param message string to be masked
   * @return message with masked token
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  public static String maskToken(String message, String token) {
    return isEmptyOrNull(message) ? message : message.replace(token, TOKEN_MASK);
  }
}
