package slydm.geektimes.training.configuration.microprofile.config.source;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * {@link Properties} 文件配置源实现
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 9:12
 */
public class PropertiesConfigSource extends MapConfigSource {

  private Properties properties;
  private String propertyFilePath;

  public PropertiesConfigSource(String propertyFilePath) {
    this("Properties Source", 500, null, propertyFilePath);
  }

  public PropertiesConfigSource(Properties properties) {
    this("Properties Source", 500, properties, null);
  }

  public PropertiesConfigSource(String name, int ordinal, Properties properties) {
    this(name, ordinal, properties, null);
  }

  public PropertiesConfigSource(String name, int ordinal, String propertyFilePath) {
    this(name, ordinal, null, propertyFilePath);
  }

  public PropertiesConfigSource(String name, int ordinal, Properties properties, String propertyFilePath) {
    super(name, ordinal);
    this.properties = properties;
    this.propertyFilePath = propertyFilePath;
  }

  @Override
  protected Map prepareConfigData() {

    Properties temp = new Properties();
    if (null != properties) {
      temp.putAll(properties);
    }

    Properties loadResult = loadSourceFromPath(propertyFilePath);
    temp.putAll(loadResult);
    return temp;
  }


  protected Properties loadSourceFromPath(String configFile) {
    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(configFile);
    try (InputStream in = resource.openStream()) {
      Properties prop = new Properties();
      prop.load(in);
      return prop;
    } catch (FileNotFoundException ex) {
      throw new IllegalArgumentException("property file not found on path: " + configFile, ex);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


}
