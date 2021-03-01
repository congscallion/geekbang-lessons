package com.slydm.thinking.in.spring.aop.features;

import com.slydm.thinking.in.spring.aop.overview.cglib.helper.EchoService;
import java.util.Arrays;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用 xml 配置方式开发 aop
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/20 18:12
 */
public class AopXmlDemo {

  public static void main(String[] args) {

    String location = "classpath:/META-INF/spring-aop-context.xml";
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(location);

    EchoService echoService = context.getBean("echoServiceProxyFactoryBean", EchoService.class);
    System.out.println(echoService);

    String[] echo = echoService.echo(new String[]{"hi"});
    System.out.println(Arrays.toString(echo));

    context.close();
  }

}
