package slydm.geektimes.training.projects.user.web.controller;

import java.sql.Connection;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.Path;
import slydm.geektimes.training.web.mvc.controller.PageController;

/**
 * 测试 jndi 数据源
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 15:59
 */
@Path("/jndi")
public class JndiDatasourceTestController implements PageController {


  @Path("")
  public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {

//    Properties env =new Properties();
//    env.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
//
//    Context initContext = new InitialContext(env);
//    Context envContext  = (Context)initContext.lookup("java:/comp/env");
//    DataSource ds = (DataSource)envContext.lookup("jdbc/UserPlatformDB");
//    Connection conn = ds.getConnection();
//    System.out.println(conn);

    return "jndi/index";
  }


}
