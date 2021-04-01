package slydm.geektimes.training.configuration.microprofile.config.source;

import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

/**
 * microprofile 默认资源文件配置源
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 9:29
 */
public class DefaultResourceConfigSource extends PropertiesConfigSource {

  private static final String configFileLocation = "META-INF/microprofile-config.properties";
  private final Logger logger = Logger.getLogger(this.getClass().getName());

  public DefaultResourceConfigSource() {
    super("Default Config File", 100, configFileLocation);
  }

  @Override
  protected Map prepareConfigData() {

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(configFileLocation);
    if (resource == null) {
      logger.info("The default config file can't be found in the classpath : " + configFileLocation);
      return null;
    }
    return loadSourceFromPath(configFileLocation);
  }
}
