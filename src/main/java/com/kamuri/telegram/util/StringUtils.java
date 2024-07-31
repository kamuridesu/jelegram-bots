package com.kamuri.telegram.util;

import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

@UtilityClass
public class StringUtils {
  public static boolean isEmptyOrNull(String expression) {
    return Objects.isNull(expression) || Strings.isBlank(expression) || Strings.isEmpty(expression);
  }
}
