package slydm.geektimes.training.projects.jmx;

/**
 * 应用名 MBean
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/18 0:01
 */
public interface ApplicationNameMBean {

  void applicationName(String name);

  String getApplicationName();

}
