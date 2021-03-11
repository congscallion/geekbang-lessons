package slydm.geektimes.training.projects.context;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import slydm.geektimes.training.projects.user.function.ThrowableAction;

/**
 * 全局组件 容器,基于 JNDI 实现
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/11 16:20
 */
public class ComponentContext {

  public static final String CONTEXT_NAME = ComponentContext.class.getName();

  private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

  private static ServletContext servletContext;

  private Context envContext;


  public void init(ServletContext servletContext) throws RuntimeException {
    ComponentContext.servletContext = servletContext;
    servletContext.setAttribute(CONTEXT_NAME, this);

    initEnvContext();
  }

  /**
   * 获取 ComponentContext
   */
  public static ComponentContext getInstance() {
    return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
  }


  public <C> C getComponent(String name) {
    try {
      return (C) envContext.lookup(name);
    } catch (NamingException e) {
      throw new RuntimeException(e);
    }
  }

  public Connection getConnection() {

    DataSource dataSource = getComponent("jdbc/UserPlatformDB");
    try {
      return dataSource.getConnection();
    } catch (SQLException throwable) {
      throw new RuntimeException(throwable);
    }

  }


  private void initEnvContext() throws RuntimeException {
    if (this.envContext != null) {
      return;
    }
    Context context = null;
    try {
      context = new InitialContext();
      this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
    } catch (NamingException e) {
      throw new RuntimeException(e);
    } finally {
      close(context);
    }
  }

  private static void close(Context context) {
    if (context != null) {
      ThrowableAction.execute(context::close);
    }
  }

}
