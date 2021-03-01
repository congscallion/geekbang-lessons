package com.slydm.thinking.in.spring.resource;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ResourceLoader;

/**
 * 注入 {@link ResourceLoader} 对象示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 14:27
 */
public class InjectingResourceLoaderDemo implements ResourceLoaderAware {

  /**
   * 方法一 通过 ResourceLoaderAware 接口回调注入
   */
  private ResourceLoader resourceLoader;

  /**
   * 自动注入
   */
  @Autowired
  private ResourceLoader autowiredResourceLoader;

  /**
   * 自动注入
   */
  @Autowired
  private AbstractApplicationContext applicationContext;

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @PostConstruct
  public void init() {
    System.out.println("resourceLoader == autowiredResourceLoader : " + (resourceLoader
        == autowiredResourceLoader));
    System.out.println(
        "resourceLoader == applicationContext : " + (resourceLoader == applicationContext));
  }


  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    // 注册当前类作为 Configuration Class
    context.register(InjectingResourceLoaderDemo.class);
    // 启动 Spring 应用上下文
    context.refresh();
    // 关闭 Spring 应用上下文
    context.close();


  }

}
