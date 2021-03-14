package slydm.geektimes.training.projects.jmx;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/14 21:29
 */
public class Hello implements HelloMBean {

  private String hello;
  private int year;

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String getHello() {
    return hello;
  }

  @Override
  public void setHello(String hello) {
    this.hello = hello;
  }
}
