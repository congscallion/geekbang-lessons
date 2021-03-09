package slydm.geektimes.training.projects.user.web.controller;

import java.io.PrintWriter;
import java.time.ZonedDateTime;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slydm.geektimes.training.projects.user.web.domin.User;
import slydm.geektimes.training.projects.user.web.repository.DatabaseUserRepositoryImpl;
import slydm.geektimes.training.projects.user.web.service.UserService;
import slydm.geektimes.training.projects.user.web.service.UserServiceImpl;
import slydm.geektimes.training.projects.user.web.util.Error;
import slydm.geektimes.training.projects.user.web.util.ErrorResponse;
import slydm.geektimes.training.web.mvc.controller.PageController;

/**
 * 用户控制器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 0:58
 */
@Path("/users")
public class UserController implements PageController {

  private final UserService userService;

  public UserController() {
    this.userService = new UserServiceImpl(new DatabaseUserRepositoryImpl());
  }


  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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


  @GET
  @Path("list")
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

      String userJson;
      try (Jsonb jsonbObject = JsonbBuilder.create()) {
        if (null != user) {
          response.setStatus(200);
          userJson = jsonbObject.toJson(user);
        } else {
          response.setStatus(404);
          ErrorResponse errorResponse = new ErrorResponse();
          Error error = new Error();
          error.setMessage("Book with ID " + id + " was not found.");
          error.setCreatedAt(ZonedDateTime.now());
          errorResponse.setError(error);
          userJson = jsonbObject.toJson(error);
        }
      }
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.print(userJson);
      out.flush();
    } catch (Exception exception) {
      logger.error(exception.getMessage());
    }
  }


}
