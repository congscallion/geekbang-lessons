package slydm.geektimes.training.projects.jmx;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/14 21:28
 */
public interface HelloMBean {

  String getHello();

  void setHello(String hello);

  void setYear(int year);


}
