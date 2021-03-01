package com.slydm.thinking.in.spring.resource;

import com.slydm.thinking.in.spring.resource.util.ResourceUtils;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

/**
 * 注入 {@link Resource} 对象示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 14:20
 */
public class InjectingResourceDemo {


  @Value("classpath:/META-INF/default.properties")
  private Resource defaultPropertiesResource;

  @Value("classpath*:/META-INF/*.properties")
  private Resource[] propertiesResources;

  @Value("${user.dir}")
  private String currentProjectRootPath;


  @PostConstruct
  public void init() {
    System.out.println(ResourceUtils.getContent(defaultPropertiesResource));
    System.out.println("================");
    Stream.of(propertiesResources).map(ResourceUtils::getContent).forEach(System.out::println);
    System.out.println("================");
    System.out.println(currentProjectRootPath);
  }

  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    // 注册当前类作为 Configuration Class
    context.register(InjectingResourceDemo.class);
    // 启动 Spring 应用上下文
    context.refresh();
    // 关闭 Spring 应用上下文
    context.close();

  }


}
