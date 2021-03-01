package com.slydm.thinking.in.spring.environment;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/2/1 8:59
 */
public class PropertySourceDemo {

  public static void main(String[] args) throws IOException, URISyntaxException {

    Properties prop = new Properties();
    prop.put("java", "java se");
    prop.setProperty("python", "webflux");
    PropertySource ps = new PropertiesPropertySource("abc", prop);

    System.out.println(ps.getName());
    System.out.println(ps.containsProperty("java"));
    System.out.println(ps.getProperty("python"));

    Map<String, Object> map = new HashMap<>();
    map.put("php", "php");
    map.put("db", "mysql");
    ps = new MapPropertySource("mp", map);
    System.out.println(ps.getName());
    System.out.println(ps.containsProperty("db"));
    System.out.println(ps.getProperty("php"));

    Resource resource = new ClassPathResource("/META-INF/a.properties");
    ResourcePropertySource rps = new ResourcePropertySource("a.properties", resource);
    System.out.println(rps.getProperty("a"));

    // TxtPropertySource
    URL url = Thread.currentThread().getContextClassLoader()
        .getResource("META-INF/p.txt");

    File file = new File(url.toURI());
    TxtPropertySource tps = new TxtPropertySource("txt.source", file, "utf-8", ":");
    System.out.println(tps.getName());
    System.out.println(Arrays.toString(tps.getPropertyNames()));
    System.out.println(tps.getProperty("x"));


  }

}
