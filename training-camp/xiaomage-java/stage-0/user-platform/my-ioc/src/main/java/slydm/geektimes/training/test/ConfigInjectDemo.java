package slydm.geektimes.training.test;

import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import slydm.geektimes.training.context.AnnotationConfigApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/6 14:02
 */
public class ConfigInjectDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(ConfigInjectDemo.class);
    applicationContext.refresh();

    applicationContext.start();

    ConfigInjectDemo configInjectDemo = applicationContext.getBean("configInjectDemo", ConfigInjectDemo.class);
    System.out.println(configInjectDemo);
    System.out.println(configInjectDemo.osName);

    applicationContext.close();

  }


  @Inject
  @ConfigProperty(name = "os.name.li")
  private String osName;


}
