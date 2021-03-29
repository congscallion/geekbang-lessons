package slydm.geektimes.training;

import slydm.geektimes.training.context.AnnotationConfigApplicationContext;
import slydm.geektimes.training.context.annotation.ComponentScan;
import slydm.geektimes.training.test.A;
import slydm.geektimes.training.test.C;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 18:37
 */
@ComponentScan(basePackage = "slydm.geektimes.training")
public class Main {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(Main.class);
    applicationContext.refresh();

    applicationContext.start();

    Object bBean = applicationContext.getBean("bBean");
    System.out.println(bBean);
    A a = applicationContext.getBean("a", A.class);
    System.out.println(a);

    a.getB().say();

    C c = applicationContext.getBean("c", C.class);
    c.say();

    applicationContext.stop();

    applicationContext.close();

  }


}
