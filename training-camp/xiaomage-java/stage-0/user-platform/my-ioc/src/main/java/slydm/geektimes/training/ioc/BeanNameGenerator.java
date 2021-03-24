package slydm.geektimes.training.ioc;

import slydm.geektimes.training.core.BeanDefinition;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/24 18:25
 */
public interface BeanNameGenerator {


  /**
   * bean name 生成器
   */
  String generateBeanName(BeanDefinition definition);

}
