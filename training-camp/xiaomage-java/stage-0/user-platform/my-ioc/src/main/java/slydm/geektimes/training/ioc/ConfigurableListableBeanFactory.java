package slydm.geektimes.training.ioc;

import slydm.geektimes.training.beans.factory.BeanPostProcessor;
import slydm.geektimes.training.configuration.microprofile.config.source.ConfigSources;
import slydm.geektimes.training.core.StringValueResolver;
import slydm.geektimes.training.exception.BeansException;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 16:15
 */
public interface ConfigurableListableBeanFactory extends BeanFactory {

  /**
   * 判断 容器中是否存在指定名称的bean
   *
   * @param beanName bean名称
   * @return true, 存在；false，不存在
   */
  boolean containsBeanDefinition(String beanName);


  /**
   * 初始化容器中所有bean
   */
  void preInstantiateSingletons() throws BeansException;


  /**
   * 根据类型获取所有bean名称
   */
  String[] getBeanNamesForType(Class<?> type);

  /**
   * 已注册的所有 bean 名称
   */
  String[] getBeanDefinitionNames();


  /**
   * 添加 BeanPostProcessor 实例
   */
  void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);


  /**
   * 注销所有单例bean
   */
  void destroySingletons();

  /**
   * 解析字符串，从 {@link ConfigSources} 中取出相应的值。
   *
   * 本实现与spring保持一致，方便以后扩展支持解析占符符的形式。
   *
   * MicroProfile 中不存在占位符，使用直接配置属性名的方式。
   *
   * @param value 待解析的字符串
   * @param propertyType 解析后的类型
   * @param parseProperty true: 表示从 {@link ConfigSources} 中解析; false: 表示不需要解析，直接类型转换
   */
  <T> T resolveEmbeddedValue(String value, Class<T> propertyType, boolean parseProperty);

  /**
   * 解析字符串，从 {@link ConfigSources} 中取出相应的值。
   *
   * 本实现与spring保持一致，方便以后扩展支持解析占符符的形式。
   *
   * MicroProfile 中不存在占位符，使用直接配置属性名的方式。
   */
  <T> T resolveEmbeddedValue(String value, Class<T> propertyType);


  /**
   * 向容器添加 {@link StringValueResolver 实现}
   */
  void addEmbeddedValueResolver(StringValueResolver valueResolver);

  /**
   * 是否存在 {@link StringValueResolver} 实例
   */
  boolean hasEmbeddedValueResolver();

}
