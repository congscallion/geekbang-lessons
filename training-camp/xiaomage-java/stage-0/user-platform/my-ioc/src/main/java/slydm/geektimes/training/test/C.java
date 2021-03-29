package slydm.geektimes.training.test;

import javax.annotation.Resource;
import slydm.geektimes.training.context.annotation.Component;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 11:21
 */
@Component
public class C {

  @Resource(name = "bBean")
  private B b;
  @Resource()
  private A a;

  public void say() {
    System.out.println("====================c========================");
    b.say();
    a.say();
    System.out.println("====================c========================");
  }

}
