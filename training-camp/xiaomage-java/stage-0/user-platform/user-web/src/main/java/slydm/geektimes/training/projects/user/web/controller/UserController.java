package slydm.geektimes.training.projects.user.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slydm.geektimes.training.projects.user.web.domin.User;
import slydm.geektimes.training.projects.user.web.repository.DatabaseUserRepositoryImpl;
import slydm.geektimes.training.projects.user.web.service.UserService;
import slydm.geektimes.training.projects.user.web.service.UserServiceImpl;
import slydm.geektimes.training.web.mvc.controller.PageController;

/**
 * 用户控制器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 0:58
 */
@Path("/users")
public class UserController extends BaseController implements PageController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController() {
    this.userService = new UserServiceImpl(new DatabaseUserRepositoryImpl());
  }


  @GET
  @Path("")
  public String index(HttpServletRequest request, HttpServletResponse response) {
    try {
      Iterable<User> users = this.userService.userList();
      request.setAttribute("pageName", "Users");
      request.setAttribute("users", users);
    } catch (Exception exception) {
      logger.error(exception.getMessage());
    }
    return "user/list";
  }


  @GET()
  @Path("list")
  @Consumes
  @Produces
  public String list(HttpServletRequest request, HttpServletResponse response) {
    try {
      Iterable<User> users = this.userService.userList();
      request.setAttribute("pageName", "Users");
      request.setAttribute("users", users);
    } catch (Exception exception) {
      logger.error(exception.getMessage());
    }
    return "book/list";
  }

  @GET
  @Path("/view")
  public void view(HttpServletRequest request, HttpServletResponse response) {

    try {
      String id = request.getParameter("id");
      long bookId = Long.parseLong(id);
      User user = this.userService.queryUserById(bookId);
      responseJson(request, response, user);
    } catch (Throwable e) {
      logger.error(e.getMessage());
    }

  }


}
