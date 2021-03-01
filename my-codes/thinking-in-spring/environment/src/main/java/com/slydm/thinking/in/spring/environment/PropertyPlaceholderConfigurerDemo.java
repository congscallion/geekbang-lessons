package com.slydm.thinking.in.spring.environment;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver;

/**
 * {@link PropertyPlaceholderConfigurer}
 *
 * 本例主要是演示在 spring 3.0 之前， 内部是如何处理点位符。
 *
 * 从api可以看出来，{@link PropertyPlaceholderConfigurer} 该类主要是负责加载资源文件以及合并处理。
 * 点位符的处理在{@link PropertyPlaceholderConfigurer.PlaceholderResolvingStringValueResolver} 类中，
 * 该类的处理依赖于{@link PropertyPlaceholderHelper}。
 *
 *
 * 即 {@link StringValueResolver#resolveStringValue(String)} 方法才具有处理占位符的能力。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/28 9:53
 */
public class PropertyPlaceholderConfigurerDemo {

  public static void main(String[] args) throws IOException {

    PropertyPlaceholderUtils placeholderUtils = new PropertyPlaceholderUtils();
    placeholderUtils.setFileEncoding("utf-8");

    placeholderUtils.setLocations(
        new ClassPathResource("META-INF/a.properties"),
        new ClassPathResource("META-INF/b.properties"),
        new ClassPathResource("META-INF/a.xml")
    );

    Properties properties = placeholderUtils.mergeProperties();
    MyPlaceholderResolvingStringValueResolver resolver = new MyPlaceholderResolvingStringValueResolver(
        properties);

    String a = resolver.resolveStringValue("hello,${a}.tip");
    System.out.println(a);


  }


}
