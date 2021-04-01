package slydm.geektimes.training.configuration.microprofile.config.source;

import java.util.Map;

/**
 * 系统属性配置源
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 9:00
 */
public class SystemPropertiesConfigSource extends MapConfigSource {

  protected SystemPropertiesConfigSource() {
    super("Java System Properties", 400);
  }

  @Override
  protected Map prepareConfigData() {
    return System.getProperties();
  }

}
