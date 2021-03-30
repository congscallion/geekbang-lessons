package slydm.geektimes.training.projects.user.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import slydm.geektimes.training.projects.jmx.ApplicationName;
import slydm.geektimes.training.web.annotation.Controller;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/12 22:42
 */
@Controller
@Path("/main")
public class MainController extends BaseController {

  @Resource
  private ApplicationName applicationName;

  /**
   * go to main page
   *
   * @param request request
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
    responseJson(request, response, applicationName);
  }

}
