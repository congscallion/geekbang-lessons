package slydm.geektimes.training.test;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import slydm.geektimes.training.context.annotation.Component;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 10:09
 */
@Component("bBean")
public class B {

  public B() {
  }

  public void say() {
    System.out.println("b say...");
  }


  @PostConstruct
  public void init() {
    System.out.println("B init ...");
  }

  @PreDestroy
  public void destroy() {
    System.out.println("B destroy ...");
  }

}
