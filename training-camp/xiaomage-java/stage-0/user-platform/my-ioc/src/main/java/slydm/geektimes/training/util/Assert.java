package slydm.geektimes.training.util;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/19 17:26
 */
public abstract class Assert {


  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void hasLength(String text, String message) {
    if (!StringUtils.hasLength(text)) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void hasText(String text, String message) {
    if (!StringUtils.hasText(text)) {
      throw new IllegalArgumentException(message);
    }
  }

}
