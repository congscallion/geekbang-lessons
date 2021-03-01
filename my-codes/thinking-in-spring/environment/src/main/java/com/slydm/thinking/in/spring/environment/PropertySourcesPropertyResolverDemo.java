package com.slydm.thinking.in.spring.environment;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;

/**
 * {@link PropertySourcesPropertyResolver} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/1 17:55
 */
public class PropertySourcesPropertyResolverDemo {

  public static void main(String[] args) throws URISyntaxException {

    // 准备数据源
    MutablePropertySources sources = new MutablePropertySources();

    Properties prop = new Properties();
    prop.setProperty("abc", "abc v1");
    PropertiesPropertySource pps = new PropertiesPropertySource("properties", prop);

    Map<String, Object> map = new HashMap<>();
    map.put("abc", "abc v2");
    MapPropertySource mps = new MapPropertySource("map", map);

    URL url = Thread.currentThread().getContextClassLoader()
        .getResource("META-INF/p.txt");
    File file = new File(url.toURI());
    TxtPropertySource tps = new TxtPropertySource("txt.source", file, "utf-8", ":");

    /**
     * 这里的配置添加充分体现了 spring 中资源优先级的概念
     */

    // 添加第一个配置
    sources.addLast(tps);
    // 把map配置添加到txt配置之前
    sources.addBefore("txt.source", mps);
    // 将property配置添加到第一位
    sources.addFirst(pps);

    PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(sources);

    String property = resolver.getProperty("abc");
    System.out.println(property);

    String result = resolver.resolvePlaceholders("${abc}");
    System.out.println(result);

    Integer count = resolver.getProperty("count", Integer.class);
    System.out.println(count);


  }


}
