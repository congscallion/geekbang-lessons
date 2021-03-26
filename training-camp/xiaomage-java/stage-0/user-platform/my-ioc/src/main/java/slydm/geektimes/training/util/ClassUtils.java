package slydm.geektimes.training.util;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/19 17:24
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

  /**
   * The inner class separator character: {@code '$'}.
   */
  private static final char INNER_CLASS_SEPARATOR = '$';

  /**
   * The CGLIB class separator: {@code "$$"}.
   */
  public static final String CGLIB_CLASS_SEPARATOR = "$$";


  public static String convertClassNameToResourcePath(String className) {
    Assert.notNull(className, "Class name must not be null");
    return className.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
  }

  /**
   * 根据类全路径获取类名
   */
  public static String getShortName(String className) {
    Assert.notNull(className, "Class name must not be empty");
    int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
    int nameEndIndex = className.indexOf(CGLIB_CLASS_SEPARATOR);
    if (nameEndIndex == -1) {
      nameEndIndex = className.length();
    }
    String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
    shortName = shortName.replace(INNER_CLASS_SEPARATOR, PACKAGE_SEPARATOR);
    return shortName;
  }

}
