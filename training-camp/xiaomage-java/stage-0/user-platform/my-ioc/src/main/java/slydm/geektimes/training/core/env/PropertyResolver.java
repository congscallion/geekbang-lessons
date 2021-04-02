package slydm.geektimes.training.core.env;

/**
 * 模拟 spring 同名接口，但本接口只声明一个可解析占位符的方法，用于从一个或多个配置源中解析值。
 *
 * @author 72089101@vivo.com(wangcong) 2021/4/2 15:34
 */
public interface PropertyResolver {


  /**
   * 是否包含指定属性
   */
  boolean containsProperty(String key);


  /**
   * 获取指定属性名的值
   */
  String getProperty(String key);

  /**
   * 获取指定属性名的值，无则返回 defaultValue
   */
  String getProperty(String key, String defaultValue);

  /**
   * 获取指定属性名的值，并类型转换
   */
  <T> T getProperty(String key, Class<T> targetType);


  /**
   * 设置占位符前缀
   */
  void setPlaceholderPrefix(String placeholderPrefix);

  /**
   * 设置占位符后缀
   */
  void setPlaceholderSuffix(String placeholderSuffix);

  /**
   * ${a.b.c:default}, 表示占位符字符串为${a.b.c}, {@link #resolvePlaceholders(String)} 实现需要从多个配置源中查找属性名为: a.b.c
   * 的属性值，如果查不到该属性值则使用字符串 "default"为结果。
   *
   * 设置占位符字符串与默认值分隔符
   */
  void setValueSeparator(String valueSeparator);

  /**
   * 解析占位符字符串并从一个或多个源中获取与该字符串同名的属性值
   */
  String resolvePlaceholders(String text);

}
