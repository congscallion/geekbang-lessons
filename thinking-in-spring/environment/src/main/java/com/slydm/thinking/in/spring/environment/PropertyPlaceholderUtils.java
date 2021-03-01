package com.slydm.thinking.in.spring.environment;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 本例主要是演示在 spring 3.0 之前， 内部是如何处理点位符。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/28 9:58
 */
public class PropertyPlaceholderUtils extends PropertyPlaceholderConfigurer {

  @Override
  public Properties mergeProperties() throws IOException {
    return super.mergeProperties();
  }


  @Override
  public String resolvePlaceholder(String placeholder, Properties props) {
    return super.resolvePlaceholder(placeholder, props);
  }

  @Override
  public String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
    return super.resolvePlaceholder(placeholder, props, systemPropertiesMode);
  }
}
