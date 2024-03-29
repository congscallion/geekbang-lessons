package slydm.geektimes.training.configuration.microprofile.config;

import static java.util.stream.StreamSupport.stream;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import slydm.geektimes.training.configuration.microprofile.config.converter.Converters;
import slydm.geektimes.training.configuration.microprofile.config.source.ConfigSources;

/**
 * {@link Config} 实现, 且不对外暴露访问权限,需要通过 builder 构造
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 11:00
 * @see DefaultConfigBuilder
 */
class DefaultConfig implements Config {

  private final ConfigSources configSources;
  private final Converters converters;

  DefaultConfig(ConfigSources configSources, Converters converters) {
    this.configSources = configSources;
    this.converters = converters;
  }

  @Override
  public <T> T getValue(String propertyName, Class<T> propertyType) {

    ConfigValue configValue = getConfigValue(propertyName);
    if (configValue == null) {
      return null;
    }
    String propertyValue = configValue.getValue();
    // String 转换成目标类型
    Converter<T> converter = doGetConverterThrows(propertyType);
    return converter == null ? null : converter.convert(propertyValue);
  }


  @Override
  public ConfigValue getConfigValue(String propertyName) {

    String propertyValue = null;
    ConfigSource configSource = null;
    Iterator<ConfigSource> iterator = configSources.iterator();
    while (iterator.hasNext()) {
      configSource = iterator.next();
      propertyValue = configSource.getValue(propertyName);
      if (propertyValue != null) {
        break;
      }
    }

    if (propertyValue == null) { // Not found
      return null;
    }

    return new DefaultConfigValue(propertyName, propertyValue, transformPropertyValue(propertyValue),
        configSource.getName(),
        configSource.getOrdinal());
  }

  /**
   * 转换属性值（如果需要）
   */
  protected String transformPropertyValue(String propertyValue) {
    return propertyValue;
  }


  @Override
  public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
    T value = getValue(propertyName, propertyType);
    return Optional.ofNullable(value);
  }

  @Override
  public Iterable<String> getPropertyNames() {
    return stream(configSources.spliterator(), false)
        .map(ConfigSource::getPropertyNames)
        .collect(LinkedHashSet::new, Set::addAll, Set::addAll);
  }


  @Override
  public Iterable<ConfigSource> getConfigSources() {
    return configSources;
  }

  @Override
  public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
    Converter converter = doGetConverter(forType);
    return converter == null ? Optional.empty() : Optional.of(converter);
  }

  protected <T> Converter<T> doGetConverterThrows(Class<T> forType) {
    Converter<T> converter = doGetConverter(forType);
    if (converter == null) {
      throw new RuntimeException("not found converter from type: " + forType);
    }
    return converter;
  }


  protected <T> Converter<T> doGetConverter(Class<T> forType) {
    List<Converter> converters = this.converters.getConverters(forType);
    return converters.isEmpty() ? null : converters.get(0);
  }


  @Override
  public <T> T unwrap(Class<T> type) {
    return null;
  }
}
