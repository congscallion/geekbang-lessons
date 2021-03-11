package slydm.geektimes.training.projects.user.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import slydm.geektimes.training.projects.user.domin.User;
import slydm.geektimes.training.projects.user.repository.DatabaseUserRepositoryImpl;
import slydm.geektimes.training.projects.user.service.UserService;
import slydm.geektimes.training.projects.user.service.UserServiceImpl;
import slydm.geektimes.training.web.mvc.controller.PageController;

/**
 * 用户控制器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 0:58
 */
@Path("/users")
public class UserController extends BaseController implements PageController {

  private final UserService userService;

  public UserController() {
    this.userService = new UserServiceImpl(new DatabaseUserRepositoryImpl());
  }

  @GET
  @Path("")
  public String index(HttpServletRequest request, HttpServletResponse response) {

    Iterable<User> users = this.userService.userList();
    request.setAttribute("pageName", "Users");
    request.setAttribute("users", users);
    return "user/list";
  }


  @GET()
  @Path("list")
  @Consumes
  @Produces
  public String list(HttpServletRequest request, HttpServletResponse response) {

    Iterable<User> users = this.userService.userList();
    request.setAttribute("pageName", "Users");
    request.setAttribute("users", users);
    return "book/list";
  }

  @GET
  @Path("/view")
  public void view(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String id = request.getParameter("id");
    long bookId = Long.parseLong(id);
    User user = this.userService.queryUserById(bookId);
    responseJson(request, response, user);
  }


}
