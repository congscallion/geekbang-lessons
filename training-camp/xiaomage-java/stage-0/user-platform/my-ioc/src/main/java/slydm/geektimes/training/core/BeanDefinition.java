package slydm.geektimes.training.core;

import io.github.classgraph.ClassInfo;

/**
 * IOC 容器中Bean的描述信息
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/19 17:13
 */
public interface BeanDefinition {

  /**
   * 获取 Bean 名称
   */
  String getBeanName();

  /**
   * 获取类元信息
   */
  ClassInfo getClassInfo();


}
