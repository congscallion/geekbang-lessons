package com.slydm.thinking.in.spring.dependency.inject.classloader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 这个例子模拟了为 spring 容器动态添加、删除 Bean 以及执行 Bean 的方法。
 * 该机制可被在线应用设计成插件机制，动态的添加或者删除插件以改变容器形为。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/18 15:27
 */
public class DifferentClassLoaderDemo {

  private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

  public static void main(String[] args) throws Exception {

    // 创建容器
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(DifferentClassLoaderDemo.class);

    // 使用 系统类加载器 加载类，用于和自定义 类加载器 加载类区分形为
    Class<?> bClass = classLoader
        .loadClass("com.slydm.thinking.in.spring.dependency.inject.classloader.B");
    AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
        .genericBeanDefinition(bClass)
        .getBeanDefinition();
    context.registerBeanDefinition("b", beanDefinition);

    // 启动容器
    context.refresh();

    // 使用 Bean
    B b = (B) context.getBean("b", bClass);
    System.out.println(b.getA());

    // 使用自定义 类加载器 加载类 并注册到 spring ioc 容器
    MyClassLoader myClassLoader = new MyClassLoader();
    Class<? extends I> cClass = (Class<? extends I>) myClassLoader
        .findClass("com.slydm.thinking.in.spring.dependency.inject.classloader.C");
    System.out.println(cClass.getClassLoader());

    AbstractBeanDefinition cBeanDefinition = BeanDefinitionBuilder
        .genericBeanDefinition(cClass)
        .getBeanDefinition();
    context.registerBeanDefinition("c", cBeanDefinition);

    // 获取 Bean
    I i = context.getBean("c", cClass);

    // 模拟消费者消费 Bean
    // 切换线程上下文类加载器，与Bean的类加载器保持一致
    ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
    Thread.currentThread().setContextClassLoader(myClassLoader);

    i.doThing();

    Method doThing2 = i.getClass().getMethod("doThing2");
    doThing2.invoke(i, new Object[0]);

    Thread.currentThread().setContextClassLoader(oldLoader);

    // 删除前
    String[] beanDefinitionNames = context.getBeanDefinitionNames();
    System.out.println(Arrays.toString(beanDefinitionNames));
    String[] singletonNames = context.getBeanFactory().getSingletonNames();
    System.out.println(Arrays.toString(singletonNames));

    context.removeBeanDefinition("c");
    System.out.println("===========================================");

    // 删除前
    beanDefinitionNames = context.getBeanDefinitionNames();
    System.out.println(Arrays.toString(beanDefinitionNames));
    singletonNames = context.getBeanFactory().getSingletonNames();
    System.out.println(Arrays.toString(singletonNames));

    context.close();


  }

  @Bean
  public A a() {
    return new A();
  }


  static class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) {
      Path path = Paths.get(
          "C:\\workspace\\thinking-in-spring\\dependency-inject\\target\\classes\\com\\slydm\\thinking\\in\\spring\\dependency\\inject\\classloader\\C.class");
      byte[] buffer = new byte[0];
      try {
        buffer = Files.readAllBytes(path);
      } catch (IOException e) {
        e.printStackTrace();
      }

      return defineClass(name, buffer, 0, buffer.length);
    }
  }
}
