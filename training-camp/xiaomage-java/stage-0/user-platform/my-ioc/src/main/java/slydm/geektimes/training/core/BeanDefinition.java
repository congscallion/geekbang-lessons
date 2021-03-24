package slydm.geektimes.training.core;

import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.MethodInfo;
import java.util.List;

/**
 * IOC 容器中Bean的描述信息
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/19 17:13
 */
public interface BeanDefinition {

  /**
   * 获取 Bean 名称
   */
  String getBeanName();

  /**
   * 获取 bean class full name
   *
   * @return
   */
  Object getBeanClass();


  /**
   * 类注解列表
   */
  AnnotationInfoList getClassAnnotationList();

  /**
   * 带有注解的字段列表
   *
   * @return
   */
  List<FieldInfo> getAnnotationFieldList();

  /**
   * 带有注解的方法列表
   *
   * @return
   */
  List<MethodInfo> getAnnotationMethodList();


}
