package slydm.geektimes.training.projects.jmx;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/18 0:02
 */
public class ApplicationName implements ApplicationNameMBean {

  private String applicationName;

  public ApplicationName() {
    this.applicationName = "Default.Application.Name";
  }


  @Override
  public void applicationName(String name) {
    this.applicationName = applicationName;

  }

  @Override
  public String getApplicationName() {
    return this.applicationName;
  }
}