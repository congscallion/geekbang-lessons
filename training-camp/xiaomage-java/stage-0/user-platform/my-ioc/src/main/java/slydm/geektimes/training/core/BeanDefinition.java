package slydm.geektimes.training.core;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
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
   * 获取 bean class full name
   *
   * @return
   */
  Object getBeanClass();


  /**
   * 类注解列表
   */
  List<AnnotationInfo> getClassAnnotationList();

  /**
   * 带有注解的字段列表
   *
   * @return
   */
  List<FieldInfo> getAnnotationFieldList();

  /**
   * 带有注解的方法列表
   */
  List<MethodInfo> getAnnotationMethodList();

  /**
   * 获取 bean 实现的接口列表
   */
  List<ClassInfo> getAllInterfaces();

  /**
   * 获取 bean 直接或间接父类列表
   */
  public List<ClassInfo> getAllSuperClasses();


}
