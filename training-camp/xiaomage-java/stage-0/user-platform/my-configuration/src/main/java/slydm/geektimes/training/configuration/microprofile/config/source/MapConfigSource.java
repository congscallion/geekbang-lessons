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
    this.name = name;
    this.ordinal = ordinal;
    this.configData = new HashMap<>();
  }

  @Override
  public Map<String, String> getProperties() {
    try {
      prepareConfigData(configData);
    } catch (Throwable cause) {
      throw new IllegalStateException("准备配置数据发生错误", cause);
    }
    return Collections.unmodifiableMap(configData);
  }

  /**
   * 默认什么也不做，提供 给子类扩展。
   */
  protected void prepareConfigData(Map<String, String> configData) {
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
