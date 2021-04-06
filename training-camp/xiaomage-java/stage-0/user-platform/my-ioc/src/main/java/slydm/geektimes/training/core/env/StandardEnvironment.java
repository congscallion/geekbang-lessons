package slydm.geektimes.training.core.env;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

/**
 * 仿 spring 同名类，但不实现与激活配置相关的方法，后续添加。
 *
 * TODO 后续根据需要实现激活配置等需求
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/2 15:46
 */
public class StandardEnvironment implements ConfigurableEnvironment, PropertyResolver {

  private final PropertyResolver propertyResolver;

  private Config config;


  public StandardEnvironment() {

    config = getConfig();
    this.propertyResolver = createPropertyResolver(config);
  }

  /**
   * 以后扩展，根据环境激活配置等信息，创建匹配的配置信息
   */
  private Config getConfig() {
    return ConfigProviderResolver.instance().getBuilder()
        .addDefaultSources()
        .addDiscoveredSources()
        .addDiscoveredConverters()
        .build();
  }


  @Override
  public boolean containsProperty(String key) {
    return this.propertyResolver.containsProperty(key);
  }

  @Override
  public String getProperty(String key) {
    return this.propertyResolver.getProperty(key);
  }

  @Override
  public String getProperty(String key, String defaultValue) {
    return this.propertyResolver.getProperty(key, defaultValue);
  }

  @Override
  public <T> T getProperty(String key, Class<T> targetType) {
    return this.propertyResolver.getProperty(key, targetType);
  }

  @Override
  public void setPlaceholderPrefix(String placeholderPrefix) {
    propertyResolver.setPlaceholderPrefix(placeholderPrefix);
  }

  @Override
  public void setPlaceholderSuffix(String placeholderSuffix) {
    propertyResolver.setPlaceholderPrefix(placeholderSuffix);
  }

  @Override
  public void setValueSeparator(String valueSeparator) {
    propertyResolver.setValueSeparator(valueSeparator);
  }

  @Override
  public String resolvePlaceholders(String text) {
    return propertyResolver.resolvePlaceholders(text);
  }

  protected PropertyResolver createPropertyResolver(Config config) {
    return new ConfigSourcesPropertyResolver(config);
  }
}
