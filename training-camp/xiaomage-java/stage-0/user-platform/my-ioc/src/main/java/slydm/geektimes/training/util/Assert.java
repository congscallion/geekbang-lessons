package slydm.geektimes.training.util;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/19 17:26
 */
public abstract class Assert {


  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

}
