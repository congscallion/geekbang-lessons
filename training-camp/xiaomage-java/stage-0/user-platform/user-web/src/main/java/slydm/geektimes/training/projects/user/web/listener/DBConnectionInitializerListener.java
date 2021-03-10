package slydm.geektimes.training.projects.user.web.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.sql.DataSource;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 21:52
 */
public class DBConnectionInitializerListener implements ServletContextListener {

  private ServletContext servletContext;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {

    servletContext = servletContextEvent.getServletContext();

    Connection connection = getConnection();

  }


  protected Connection getConnection() {
    Context context = null;
    Connection connection = null;

    try {

      context = new InitialContext();
      Context envContext = (Context) context.lookup("java:comp/env");
      DataSource dataSource = (DataSource) envContext.lookup("jdbc/UserPlatformDB");

      connection = dataSource.getConnection();

    } catch (NamingException | SQLException ex) {

      servletContext.log(ex.getMessage(), ex);
    }

    return connection;
  }



  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
