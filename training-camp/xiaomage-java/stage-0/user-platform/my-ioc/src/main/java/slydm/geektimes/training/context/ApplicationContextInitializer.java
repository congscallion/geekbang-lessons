package slydm.geektimes.training.context;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 17:17
 */
public interface ApplicationContextInitializer<C extends ApplicationContext> {

  /**
   * Initialize the given application context.
   *
   * @param applicationContext the application to configure
   */
  void initialize(C applicationContext);

}
