package slydm.geektimes.training.configuration.microprofile.config.source;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * 基于 {@link Map}的 {@link ConfigSource} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/31 18:26
 */
public class MapConfigSource implements ConfigSource {

  private final String name;
  private final int ordinal;
  private final Map<String, String> configData;

  protected MapConfigSource(String name, int ordinal) {
    this(name, ordinal, new HashMap<>());
  }

  protected MapConfigSource(String name, int ordinal, Map<String, String> sourceData) {
    this.name = name;
    this.ordinal = ordinal;
    if (sourceData == null) {
      throw new IllegalArgumentException("sourceData can not null");
    }
    this.configData = sourceData;
    populateConfigSource();
  }

  @Override
  public Map<String, String> getProperties() {
    return Collections.unmodifiableMap(configData);
  }

  private void populateConfigSource() {
    try {
      Map map = prepareConfigData();
      if (null != map) {
        this.configData.putAll(map);
      }
    } catch (Throwable cause) {
      throw new IllegalStateException("准备配置数据发生错误", cause);
    }

  }

  /**
   * 默认什么也不做，提供 给子类扩展。
   */
  protected Map<String, String> prepareConfigData() {
    return new HashMap();
  }

  @Override
  public Set<String> getPropertyNames() {
    return configData.keySet();
  }

  @Override
  public String getValue(String propertyName) {
    return configData.get(propertyName);
  }

  @Override
  public int getOrdinal() {
    return ordinal;
  }

  @Override
  public String getName() {
    return name;
  }
}
