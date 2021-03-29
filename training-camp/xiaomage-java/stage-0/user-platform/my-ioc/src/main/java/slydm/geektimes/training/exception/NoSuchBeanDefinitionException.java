package slydm.geektimes.training.exception;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/26 19:36
 */
public class NoSuchBeanDefinitionException extends BeansException {

  private final String beanName;


  public NoSuchBeanDefinitionException(String name) {
    super("No bean named '" + name + "' available");
    this.beanName = name;
  }

  public NoSuchBeanDefinitionException(String name, String message) {
    super("No bean named '" + name + "' available: " + message);
    this.beanName = name;
  }

  public String getBeanName() {
    return this.beanName;
  }

}
