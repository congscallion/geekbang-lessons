package slydm.geektimes.training.projects.user.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import slydm.geektimes.training.projects.context.ComponentContext;
import slydm.geektimes.training.projects.jmx.ApplicationName;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/12 22:42
 */
@Path("/main")
public class MainController extends BaseController {

  /**
   * go to main page
   *
   * @param request  request
   * @param response response
   * @return /WEB-INF/index.jsp
   */
  @GET
  @Path("")
  public String index(HttpServletRequest request, HttpServletResponse response) {
    return "/WEB-INF/index";
  }

  @GET
  @Path("/application/name")
  public void applicationName(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ApplicationName applicationName = ComponentContext.getInstance().getComponent("bean/ApplicationName");
    responseJson(request, response, applicationName);
  }

}
