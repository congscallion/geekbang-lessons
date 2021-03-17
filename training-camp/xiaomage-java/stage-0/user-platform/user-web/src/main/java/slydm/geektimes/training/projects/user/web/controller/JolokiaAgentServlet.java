package slydm.geektimes.training.projects.user.web.controller;

import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.jolokia.http.AgentServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slydm.geektimes.training.projects.context.ComponentContext;
import slydm.geektimes.training.projects.jmx.ApplicationName;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/18 0:09
 */
public class JolokiaAgentServlet extends AgentServlet {

  private static final Logger logger = LoggerFactory.getLogger(JolokiaAgentServlet.class);

  @Override
  public void init(ServletConfig pServletConfig) throws ServletException {
    try {
      ApplicationName applicationName = ComponentContext.getInstance().getComponent("bean/ApplicationName");

      ObjectName objectName = new ObjectName("slydm.geektimes.training.projects.jmx.ApplicationName:a=b");
      MBeanServer server = ManagementFactory.getPlatformMBeanServer();
      server.registerMBean(applicationName, objectName);
    } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
        MBeanRegistrationException | NotCompliantMBeanException e) {
      // handle exceptions
      logger.error("注册 ApplicationName MBean 失败:", e);
    }
    super.init(pServletConfig);

  }
}
