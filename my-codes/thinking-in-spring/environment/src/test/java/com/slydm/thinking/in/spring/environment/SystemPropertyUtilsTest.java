package com.slydm.thinking.in.spring.environment;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.SystemPropertyUtils;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/2/1 17:50
 */
public class SystemPropertyUtilsTest {


  @Test
  public void test1() {

    Assert.assertEquals("wangcong", SystemPropertyUtils.resolvePlaceholders("${user.name}"));
    Assert.assertEquals("C:\\Install\\Java\\jdk1.8.0_201\\jre", SystemPropertyUtils.resolvePlaceholders("${java.home}"));
    Assert.assertEquals("1.8.0_201", SystemPropertyUtils.resolvePlaceholders("${java.version}"));

  }

}
