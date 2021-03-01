package com.slydm.thinking.in.spring.configuration.metadata;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * YAML 格式的 {@link PropertySourceFactory} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 11:17
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

  @Override
  public PropertySource<?> createPropertySource(String name, EncodedResource resource)
      throws IOException {

    YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
    yamlPropertiesFactoryBean.setResources(resource.getResource());
    Properties yamlProperties = yamlPropertiesFactoryBean.getObject();
    return new PropertiesPropertySource(name, yamlProperties);

  }
}
