package slydm.geektimes.training.projects.user.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import slydm.geektimes.training.web.annotation.Controller;

/**
 * 测试 jndi 数据源
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 15:59
 */
@Controller
@Path("/jndi")
public class JndiDatasourceTestController {


  @Path("")
  public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {

    return "jndi/index";
  }


}
