package slydm.geektimes.training.configuration.microprofile.config.source;

import static java.util.ServiceLoader.load;
import static java.util.stream.Stream.of;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.StreamSupport;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * 本实现与spring保持一致，可以编排配置源的顺序，以及动态的在列表头和尾添加配置源
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 9:47
 */
public class ConfigSources implements Iterable<ConfigSource> {

  /**
   * 是否已加载默认的配置源
   */
  private boolean addedDefaultConfigSources;

  /**
   * 是否已添加配置源发现器
   */
  private boolean addedDiscoveredConfigSources;

  /**
   * 配置源列表
   */
  private final List<ConfigSource> configSourceList = new CopyOnWriteArrayList<>();

  /**
   * classloader,用于资源加载
   */
  private ClassLoader classLoader;

  public ConfigSources(ClassLoader classLoader) {
    this(classLoader, null);
  }

  public ConfigSources(ClassLoader classLoader, ConfigSources configSources) {
    this.classLoader = classLoader;
    if (null != configSources) {
      for (ConfigSource configSource : configSources) {
        addLast(configSource);
      }
    }
  }


  /**
   * 添加通过spi的方式配置的配置源
   */
  public void addDiscoveredSources() {
    if (addedDiscoveredConfigSources) {
      return;
    }

    ClassLoader cl = this.classLoader;
    if (cl == null) {
      try {
        cl = Thread.currentThread().getContextClassLoader();
      } catch (Throwable ex) {
        // Cannot access thread context ClassLoader - falling back...
      }

      if (cl == null) {
        // No thread context class loader -> use class loader of this class.
        cl = ConfigSources.class.getClassLoader();
        if (cl == null) {
          // getClassLoader() returning null indicates the bootstrap ClassLoader
          try {
            cl = ClassLoader.getSystemClassLoader();
          } catch (Throwable ex) {
            // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
          }
        }
      }
    }

    ServiceLoader<ConfigSource> load = load(ConfigSource.class, classLoader);
    addConfigSources(load);
    addedDiscoveredConfigSources = true;
  }

  public void addConfigSources(ConfigSource... configSources) {
    addConfigSources(Arrays.asList(configSources));
  }

  public void addConfigSources(Iterable<ConfigSource> configSources) {
    StreamSupport.stream(configSources.spliterator(), false)
        .sorted(ConfigSourceOrdinalComparator.comparing(ConfigSource::getOrdinal))
        .forEach(this::addLast);
  }
  
  /**
   * 添加 microprofile 默认的配置次
   */
  public void addDefaultSources() {
    if (addedDefaultConfigSources) {
      return;
    }
    addConfigSources(
        SystemPropertiesConfigSource.class,
        SystemEnvironmentConfigSource.class,
        DefaultResourceConfigSource.class
    );
    addedDefaultConfigSources = true;
  }


  private void addConfigSources(Class<? extends ConfigSource>... configSourceClasses) {
    of(configSourceClasses)
        .map(this::newInstance)
        .sorted(ConfigSourceOrdinalComparator.comparing(ConfigSource::getOrdinal))
        .forEach(this::addLast);
  }

  private ConfigSource newInstance(Class<? extends ConfigSource> configSourceClass) {
    ConfigSource instance = null;
    try {
      instance = configSourceClass.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
    return instance;
  }

  public void addLast(ConfigSource configSource) {
    removeIfPresent(configSource);
    this.configSourceList.add(configSource);
  }

  public void addFirst(ConfigSource configSource) {
    removeIfPresent(configSource);
    this.configSourceList.add(0, configSource);
  }

  public void addBefore(String relativePropertySourceName, ConfigSource configSource) {
    assertLegalRelativeAddition(relativePropertySourceName, configSource);
    removeIfPresent(configSource);
    int index = assertPresentAndGetIndex(relativePropertySourceName);
    addAtIndex(index, configSource);
  }

  public void addAfter(String relativePropertySourceName, ConfigSource configSource) {
    assertLegalRelativeAddition(relativePropertySourceName, configSource);
    removeIfPresent(configSource);
    int index = assertPresentAndGetIndex(relativePropertySourceName);
    addAtIndex(index + 1, configSource);
  }

  private void addAtIndex(int index, ConfigSource configSource) {
    removeIfPresent(configSource);
    this.configSourceList.add(index, configSource);
  }

  public ConfigSource remove(String name) {
    int index = getIndex(name);
    return (index != -1 ? this.configSourceList.remove(index) : null);
  }

  public void replace(String name, ConfigSource configSource) {
    int index = assertPresentAndGetIndex(name);
    this.configSourceList.set(index, configSource);
  }

  public boolean contains(String name) {
    int index = getIndex(name);
    return index != -1;
  }


  public int size() {
    return this.configSourceList.size();
  }

  @Override
  public String toString() {
    return this.configSourceList.toString();
  }


  private int assertPresentAndGetIndex(String name) {

    int index = getIndex(name);
    if (index == -1) {
      throw new IllegalArgumentException("PropertySource named '" + name + "' does not exist");
    }
    return index;
  }

  private int getIndex(String name) {
    int index = -1;
    for (int i = 0; i < this.configSourceList.size(); i++) {
      if (name.equals(this.configSourceList.get(i).getName())) {
        index = i;
        break;
      }
    }
    return index;
  }


  /**
   * 判断两个相邻的sourceName是否一致，一致，不能添加
   */
  protected void assertLegalRelativeAddition(String relativeConfigSourceName, ConfigSource configSource) {
    String newConfigSourceName = configSource.getName();
    if (relativeConfigSourceName.equals(newConfigSourceName)) {
      throw new IllegalArgumentException(
          "PropertySource named '" + newConfigSourceName + "' cannot be added relative to itself");
    }
  }


  protected void removeIfPresent(ConfigSource configSource) {
    this.configSourceList.remove(configSource);
  }


  @Override
  public Iterator<ConfigSource> iterator() {
    return this.configSourceList.iterator();
  }

  public boolean isAddedDefaultConfigSources() {
    return addedDefaultConfigSources;
  }

  public boolean isAddedDiscoveredConfigSources() {
    return addedDiscoveredConfigSources;
  }

  public ClassLoader getClassLoader() {
    return classLoader;
  }

  public void setClassLoader(ClassLoader loader) {
    this.classLoader = classLoader;
  }
}
