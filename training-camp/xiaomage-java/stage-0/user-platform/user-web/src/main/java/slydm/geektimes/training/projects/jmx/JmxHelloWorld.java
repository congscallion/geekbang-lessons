package slydm.geektimes.training.projects.jmx;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * Jmx hello world 程序示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/14 21:25
 */
public class JmxHelloWorld {


  public static void main(String[] args) {
    ApplicationName hello = new ApplicationName();
    try {
      ObjectName objectName = new ObjectName("slydm.geektimes.training.projects.jmx.ApplicationName:aa=bb");
      MBeanServer server = ManagementFactory.getPlatformMBeanServer();

      server.registerMBean(hello, objectName);
    } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
        MBeanRegistrationException | NotCompliantMBeanException e) {
      // handle exceptions
    }

    while (true) {

      try {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("application.name=" + hello.getApplicationName());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }


    }

  }

}
