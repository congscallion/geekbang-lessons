package slydm.geektimes.training.core;

/**
 * IOC 容器中Bean的描述信息
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/19 17:13
 */
public class BeanDefinition {

  private String beanClassName;

  public String getBeanClassName() {
    return beanClassName;
  }

  public void setBeanClassName(String beanClassName) {
    this.beanClassName = beanClassName;
  }
}
