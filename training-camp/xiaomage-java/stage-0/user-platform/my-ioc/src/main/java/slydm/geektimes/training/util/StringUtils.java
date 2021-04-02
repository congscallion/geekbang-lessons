package slydm.geektimes.training.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/19 18:05
 */
public abstract class StringUtils {

  public static boolean hasLength(String str) {
    return (str != null && !str.isEmpty());
  }

  public static boolean hasText(String str) {
    return (str != null && !str.isEmpty() && containsText(str));
  }

  private static boolean containsText(CharSequence str) {
    int strLen = str.length();
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return true;
      }
    }
    return false;
  }


  public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
    if (index + substring.length() > str.length()) {
      return false;
    }
    for (int i = 0; i < substring.length(); i++) {
      if (str.charAt(index + i) != substring.charAt(i)) {
        return false;
      }
    }
    return true;
  }


  public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
      boolean ignoreEmptyTokens) {

    if (str == null) {
      return new String[0];
    }

    StringTokenizer st = new StringTokenizer(str, delimiters);
    List<String> tokens = new ArrayList<>();
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (trimTokens) {
        token = token.trim();
      }
      if (!ignoreEmptyTokens || token.length() > 0) {
        tokens.add(token);
      }
    }
    return toStringArray(tokens);
  }

  public static String[] toStringArray(Collection<String> collection) {
    return collection.toArray(new String[0]);
  }

  /**
   * Replace all occurrences of a substring within a string with another string.
   *
   * @param inString {@code String} to examine
   * @param oldPattern {@code String} to replace
   * @param newPattern {@code String} to insert
   * @return a {@code String} with the replacements
   */
  public static String replace(String inString, String oldPattern, String newPattern) {
    if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
      return inString;
    }
    int index = inString.indexOf(oldPattern);
    if (index == -1) {
      // no occurrence -> can return input as-is
      return inString;
    }

    int capacity = inString.length();
    if (newPattern.length() > oldPattern.length()) {
      capacity += 16;
    }
    StringBuilder sb = new StringBuilder(capacity);

    int pos = 0;  // our position in the old string
    int patLen = oldPattern.length();
    while (index >= 0) {
      sb.append(inString.substring(pos, index));
      sb.append(newPattern);
      pos = index + patLen;
      index = inString.indexOf(oldPattern, pos);
    }

    // append any characters to the right of a match
    sb.append(inString.substring(pos));
    return sb.toString();
  }

  /**
   * 比较两个字符串是否相等且忽略大小写
   */
  public static boolean equalsIgnoreCase(final CharSequence str1, final CharSequence str2) {
    if (str1 == null || str2 == null) {
      return str1 == str2;
    } else if (str1 == str2) {
      return true;
    } else if (str1.length() != str2.length()) {
      return false;
    } else {
      return regionMatches(str1, true, 0, str2, 0, str1.length());
    }
  }


  /**
   * 比较两个字符串是否相等且不忽略大小写
   */
  public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
    if (cs1 == cs2) {
      return true;
    }
    if (cs1 == null || cs2 == null) {
      return false;
    }
    if (cs1.length() != cs2.length()) {
      return false;
    }
    if (cs1 instanceof String && cs2 instanceof String) {
      return cs1.equals(cs2);
    }
    return regionMatches(cs1, false, 0, cs2, 0, cs1.length());
  }

  static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
      final CharSequence substring, final int start, final int length) {
    if (cs instanceof String && substring instanceof String) {
      return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
    }
    int index1 = thisStart;
    int index2 = start;
    int tmpLen = length;

    // Extract these first so we detect NPEs the same as the java.lang.String version
    final int srcLen = cs.length() - thisStart;
    final int otherLen = substring.length() - start;

    // Check for invalid parameters
    if (thisStart < 0 || start < 0 || length < 0) {
      return false;
    }

    // Check that the regions are long enough
    if (srcLen < length || otherLen < length) {
      return false;
    }

    while (tmpLen-- > 0) {
      final char c1 = cs.charAt(index1++);
      final char c2 = substring.charAt(index2++);

      if (c1 == c2) {
        continue;
      }

      if (!ignoreCase) {
        return false;
      }

      // The same check as in String.regionMatches():
      if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
          && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
        return false;
      }
    }

    return true;
  }

}
