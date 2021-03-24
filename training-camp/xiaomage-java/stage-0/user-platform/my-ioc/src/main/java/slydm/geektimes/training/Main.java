package slydm.geektimes.training;

import slydm.geektimes.training.context.AnnotationConfigApplicationContext;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/24 18:37
 */
public class Main {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.start();


    applicationContext.close();


  }


}
