package slydm.geektimes.training.exception;

import java.util.List;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/27 16:31
 */
public class BeanCreationException extends BeansException {

  private final String beanName;
  private final String resourceDescription;
  private List<Throwable> relatedCauses;


  /**
   * Create a new BeanCreationException.
   *
   * @param msg the detail message
   */
  public BeanCreationException(String msg) {
    super(msg);
    this.beanName = null;
    this.resourceDescription = null;
  }

  /**
   * Create a new BeanCreationException.
   *
   * @param msg the detail message
   * @param cause the root cause
   */
  public BeanCreationException(String msg, Throwable cause) {
    super(msg, cause);
    this.beanName = null;
    this.resourceDescription = null;
  }

  public String getBeanName() {
    return beanName;
  }

  public String getResourceDescription() {
    return resourceDescription;
  }

  public List<Throwable> getRelatedCauses() {
    return relatedCauses;
  }

  /**
   * Create a new BeanCreationException.
   *
   * @param beanName the name of the bean requested
   * @param msg the detail message
   */
  public BeanCreationException(String beanName, String msg) {
    super("Error creating bean with name '" + beanName + "': " + msg);
    this.beanName = beanName;
    this.resourceDescription = null;
  }


}
