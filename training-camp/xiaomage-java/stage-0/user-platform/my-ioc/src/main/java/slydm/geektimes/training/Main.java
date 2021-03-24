package slydm.geektimes.training;

import slydm.geektimes.training.context.AnnotationConfigApplicationContext;
import slydm.geektimes.training.test.A;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 18:37
 */
public class Main {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.start();

    Object bBean = applicationContext.getBean("bBean");
    System.out.println(bBean);
    A a = applicationContext.getBean("a", A.class);
    System.out.println(a);

    a.getB().say();

    applicationContext.close();


  }


}
