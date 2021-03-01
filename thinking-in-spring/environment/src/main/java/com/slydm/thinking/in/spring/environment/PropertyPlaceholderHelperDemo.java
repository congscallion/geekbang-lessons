package com.slydm.thinking.in.spring.environment;

import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.SystemPropertyUtils;

/**
 * spring 中真正处理占位符的工具类
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/1 18:18
 */
public class PropertyPlaceholderHelperDemo {

  public static void main(String[] args) {

    PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
        SystemPropertyUtils.PLACEHOLDER_PREFIX, SystemPropertyUtils.PLACEHOLDER_SUFFIX,
        SystemPropertyUtils.VALUE_SEPARATOR, true);

    String result = helper.replacePlaceholders("${a.b.c}", s -> s);
    System.out.println(result); // a.b.c

    result = helper.replacePlaceholders("${a.b.c}", s -> s + ".d");
    System.out.println(result); // a.b.c.d

  }

}
