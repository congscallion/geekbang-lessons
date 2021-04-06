package slydm.geektimes.training.core.env;

import java.util.Set;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import slydm.geektimes.training.util.PropertyPlaceholderHelper;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/2 16:16
 */
public class ConfigSourcesPropertyResolver implements PropertyResolver {

  /**
   * Default placeholder prefix: {@value}.
   */
  public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

  /**
   * Default placeholder suffix: {@value}.
   */
  public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

  /**
   * Default value separator: {@value}.
   */
  public static final String DEFAULT_VALUE_SEPARATOR = ":";

  /**
   * Defaults to {@value #DEFAULT_PLACEHOLDER_PREFIX}.
   */
  protected String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;

  /**
   * Defaults to {@value #DEFAULT_PLACEHOLDER_SUFFIX}.
   */
  protected String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;

  /**
   * Defaults to {@value #DEFAULT_VALUE_SEPARATOR}.
   */
  protected String valueSeparator = DEFAULT_VALUE_SEPARATOR;

  /**
   * 延迟创建，当需要使用占符位解析时才创建。
   */
  private PropertyPlaceholderHelper nonStrictHelper;


  private Config config;

  public ConfigSourcesPropertyResolver(Config config) {
    this.config = config;
  }

  @Override
  public boolean containsProperty(String key) {
    return ((Set) config.getPropertyNames()).contains(key);
  }

  @Override
  public String getProperty(String key) {
    return getProperty(key, "");
  }

  @Override
  public String getProperty(String key, String defaultValue) {

    ConfigValue configValue = config.getConfigValue(key);
    if (null == configValue) {
      return defaultValue;
    }
    return configValue.getValue();
  }

  @Override
  public <T> T getProperty(String key, Class<T> targetType) {
    return config.getValue(key, targetType);
  }

  @Override
  public void setPlaceholderPrefix(String placeholderPrefix) {
    this.placeholderPrefix = placeholderPrefix;
  }

  @Override
  public void setPlaceholderSuffix(String placeholderSuffix) {
    this.placeholderSuffix = placeholderSuffix;
  }

  @Override
  public void setValueSeparator(String valueSeparator) {
    this.valueSeparator = valueSeparator;
  }

  @Override
  public String resolvePlaceholders(String text) {
    if (this.nonStrictHelper == null) {
      this.nonStrictHelper = createPlaceholderHelper(true);
    }
    return doResolvePlaceholders(text, this.nonStrictHelper);
  }

  private String doResolvePlaceholders(String text, PropertyPlaceholderHelper helper) {
    return helper.replacePlaceholders(text, this::getProperty);
  }

  private PropertyPlaceholderHelper createPlaceholderHelper(boolean ignoreUnresolvablePlaceholders) {
    return new PropertyPlaceholderHelper(this.placeholderPrefix, this.placeholderSuffix,
        this.valueSeparator, ignoreUnresolvablePlaceholders);
  }
}
