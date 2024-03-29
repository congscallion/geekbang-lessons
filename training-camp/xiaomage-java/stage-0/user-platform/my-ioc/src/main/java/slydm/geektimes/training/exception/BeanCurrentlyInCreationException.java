package slydm.geektimes.training.exception;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/27 16:34
 */
public class BeanCurrentlyInCreationException extends BeanCreationException {

  /**
   * Create a new BeanCurrentlyInCreationException,
   * with a default error message that indicates a circular reference.
   *
   * @param beanName the name of the bean requested
   */
  public BeanCurrentlyInCreationException(String beanName) {
    super(beanName, "Requested bean is currently in creation: Is there an unresolvable circular reference?");
  }

  /**
   * Create a new BeanCurrentlyInCreationException.
   *
   * @param beanName the name of the bean requested
   * @param msg the detail message
   */
  public BeanCurrentlyInCreationException(String beanName, String msg) {
    super(beanName, msg);
  }
}
