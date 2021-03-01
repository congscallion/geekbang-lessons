package com.slydm.thinking.in.spring.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 简单版本的自定义 {@link MessageSource} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/6 16:39
 */
public class MyPropertiesMessageSource extends AbstractMessageSource {

  private static final String PROPERTIES_SUFFIX = ".properties";
  private ResourceLoader resourceLoader = new DefaultResourceLoader();
  private String baseName;

  private final ConcurrentMap<Locale, Properties> cachedMergedProperties = new ConcurrentHashMap<>();

  public MyPropertiesMessageSource() {
  }

  public void setBaseName(String baseName) {
    this.baseName = baseName;
  }


  @Override
  protected MessageFormat resolveCode(String code, Locale locale) {

    Properties properties = cachedMergedProperties.get(locale);
    if (null == properties) {

      properties = loadProp(locale);

      if (null == properties) {
        return null;
      }

      cachedMergedProperties.putIfAbsent(locale, properties);
    }

    return new MessageFormat(properties.getProperty(code), locale);
  }

  private Properties loadProp(Locale locale) {

    String fileName =
        baseName + "_" + locale.getLanguage() + "_" + locale.getCountry() + PROPERTIES_SUFFIX;

    Resource resource = resourceLoader.getResource(fileName);
    if (!resource.exists()) {
      throw new RuntimeException(fileName + " file not found on classpath.");
    }

    Properties properties = new Properties();
    try (InputStream in = resource.getInputStream()) {
      properties.load(in);
    } catch (IOException e) {
    }
    return properties.isEmpty() ? null : properties;
  }
}
