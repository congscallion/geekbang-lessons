package slydm.geektimes.training.util;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/19 17:24
 */
public abstract class ClassUtils {


  /**
   * The package separator character: {@code '.'}.
   */
  private static final char PACKAGE_SEPARATOR = '.';

  /**
   * The path separator character: {@code '/'}.
   */
  private static final char PATH_SEPARATOR = '/';


  public static String convertClassNameToResourcePath(String className) {
    Assert.notNull(className, "Class name must not be null");
    return className.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
  }

}
