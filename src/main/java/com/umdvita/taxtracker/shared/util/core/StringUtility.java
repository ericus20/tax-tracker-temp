package com.umdvita.taxtracker.shared.util.core;

import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

public abstract class StringUtility extends StringUtils {

  public static String capitalize(@NonNull String string) {
    if (!isEmpty(string)) {
      return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    return "";
  }

  public static String leftPadWithCharacter(String originalString, String character, int length) {
    String padding = character.repeat(Math.max(0, length));
    return padding.substring(originalString.length()) + originalString;
  }

  public static boolean isNumeric(String strNum) {
    try {
      Double.parseDouble(strNum);
    } catch (NumberFormatException | NullPointerException nfe) {
      return false;
    }
    return true;
  }
}
