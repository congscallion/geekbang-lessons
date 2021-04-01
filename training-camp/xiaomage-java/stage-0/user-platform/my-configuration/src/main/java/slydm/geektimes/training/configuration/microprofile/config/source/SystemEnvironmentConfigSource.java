package slydm.geektimes.training.configuration.microprofile.config.source;

import java.util.Map;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 9:07
 */
public class SystemEnvironmentConfigSource extends MapConfigSource {

  protected SystemEnvironmentConfigSource() {
    super("System Environment Variables", 300);
  }

  @Override
  protected Map prepareConfigData() {
    return System.getenv();
  }
}
