package slydm.geektimes.training.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

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

  public static ClassLoader getDefaultClassLoader() {
    ClassLoader cl = null;
    try {
      cl = Thread.currentThread().getContextClassLoader();
    } catch (Throwable ex) {
      // Cannot access thread context ClassLoader - falling back...
    }
    if (cl == null) {
      // No thread context class loader -> use class loader of this class.
      cl = ClassUtils.class.getClassLoader();
      if (cl == null) {
        // getClassLoader() returning null indicates the bootstrap ClassLoader
        try {
          cl = ClassLoader.getSystemClassLoader();
        } catch (Throwable ex) {
          // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
        }
      }
    }
    return cl;
  }

  public static Class<?> forName(String name, ClassLoader classLoader)
      throws ClassNotFoundException, LinkageError {

    Assert.notNull(name, "Name must not be null");

    Class<?> clazz = resolvePrimitiveClassName(name);
    if (clazz == null) {
      clazz = commonClassCache.get(name);
    }
    if (clazz != null) {
      return clazz;
    }

    // "java.lang.String[]" style arrays
    if (name.endsWith(ARRAY_SUFFIX)) {
      String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
      Class<?> elementClass = forName(elementClassName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    // "[Ljava.lang.String;" style arrays
    if (name.startsWith(NON_PRIMITIVE_ARRAY_PREFIX) && name.endsWith(";")) {
      String elementName = name.substring(NON_PRIMITIVE_ARRAY_PREFIX.length(), name.length() - 1);
      Class<?> elementClass = forName(elementName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    // "[[I" or "[[Ljava.lang.String;" style arrays
    if (name.startsWith(INTERNAL_ARRAY_PREFIX)) {
      String elementName = name.substring(INTERNAL_ARRAY_PREFIX.length());
      Class<?> elementClass = forName(elementName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    ClassLoader clToUse = classLoader;
    if (clToUse == null) {
      clToUse = getDefaultClassLoader();
    }
    try {
      return Class.forName(name, false, clToUse);
    } catch (ClassNotFoundException ex) {
      int lastDotIndex = name.lastIndexOf(PACKAGE_SEPARATOR);
      if (lastDotIndex != -1) {
        String nestedClassName =
            name.substring(0, lastDotIndex) + NESTED_CLASS_SEPARATOR + name.substring(lastDotIndex + 1);
        try {
          return Class.forName(nestedClassName, false, clToUse);
        } catch (ClassNotFoundException ex2) {
          // Swallow - let original exception get through
        }
      }
      throw ex;
    }
  }

  public static Class<?> resolvePrimitiveClassName(String name) {
    Class<?> result = null;
    // Most class names will be quite long, considering that they
    // SHOULD sit in a package, so a length check is worthwhile.
    if (name != null && name.length() <= 7) {
      // Could be a primitive - likely.
      result = primitiveTypeNameMap.get(name);
    }
    return result;
  }

  private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<>(32);

  private static final Map<String, Class<?>> commonClassCache = new HashMap<>(64);

  /**
   * Suffix for array class names: {@code "[]"}.
   */
  public static final String ARRAY_SUFFIX = "[]";

  /**
   * Prefix for internal array class names: {@code "["}.
   */
  private static final String INTERNAL_ARRAY_PREFIX = "[";

  /**
   * Prefix for internal non-primitive array class names: {@code "[L"}.
   */
  private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

  /**
   * The nested class separator character: {@code '$'}.
   */
  private static final char NESTED_CLASS_SEPARATOR = '$';
}
