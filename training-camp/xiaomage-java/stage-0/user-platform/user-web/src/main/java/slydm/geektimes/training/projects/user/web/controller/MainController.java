package slydm.geektimes.training.projects.user.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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

}
