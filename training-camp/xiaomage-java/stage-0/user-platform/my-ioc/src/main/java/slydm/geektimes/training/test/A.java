package slydm.geektimes.training.test;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import slydm.geektimes.training.context.annotation.Component;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/24 10:09
 */
@Component
public class A {

  private String name;
  private String email;
  private Long age;

  public A(String name) {
    this.name = name;
  }

  public A(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public A(String name, String email, Long age) {
    this.name = name;
    this.email = email;
    this.age = age;
  }


  @Resource(name = "bBean")
  private B b;

  @PostConstruct
  public void init() {
    System.out.println("A init ...");
  }

  @PreDestroy
  public void destroy() {
    System.out.println("A destroy ...");
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getAge() {
    return age;
  }

  public void setAge(Long age) {
    this.age = age;
  }

  @Resource
  public void setB(B b) {
    this.b = b;
  }

  public B getB() {
    return b;
  }
}
