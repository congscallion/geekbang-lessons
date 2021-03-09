package slydm.geektimes.training.projects.user.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import slydm.geektimes.training.web.mvc.controller.PageController;

/**
 * @author wangcymy@gmail.com(wangcong) 3/4/21 10:47 PM
 */
@Path("/hello")
public class HelloWorldController implements PageController {

  @Path("/world")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
    return "index";
  }
}
