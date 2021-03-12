package slydm.geektimes.training.projects.user.web.controller;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import slydm.geektimes.training.projects.context.ComponentContext;
import slydm.geektimes.training.projects.user.domin.User;
import slydm.geektimes.training.projects.user.exception.WebError;
import slydm.geektimes.training.projects.user.service.UserService;
import slydm.geektimes.training.projects.user.web.Result;
import slydm.geektimes.training.web.mvc.controller.PageController;

/**
 * 用户控制器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 0:58
 */
@Path("/users")
public class UserController extends BaseController implements PageController {

  private final UserService userService;
  private final Validator validator;

  public UserController() {
    userService = ComponentContext.getInstance().getComponent("bean/UserService");
    validator = ComponentContext.getInstance().getComponent("bean/Validator");
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
  public String list(HttpServletRequest request, HttpServletResponse response) {

    Iterable<User> users = this.userService.userList();
    request.setAttribute("pageName", "Users");
    request.setAttribute("users", users);
    return "user/list";
  }

  @GET
  @Path("/view")
  public void view(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String id = request.getParameter("id");
    long bookId = Long.parseLong(id);
    User user = this.userService.queryUserById(bookId);
    responseJson(request, response, user);
  }

  @POST
  @Path("/register")
  public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {

    User user = getUserFromRequest(request);
    String err = validUser(user);
    if (!err.isEmpty()) {
      responseJson(request, response, Result.error(err));
      return;
    }
    userService.register(user);
    responseJson(request, response, Result.success());
  }


  @PUT
  @Path("/update")
  public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

    User user = getUserFromRequest(request);
    Long userId = Long.parseLong(request.getParameter("id"));
    user.setId(userId);
    String err = validUser(user);
    if (!err.isEmpty()) {
      responseJson(request, response, Result.error(err));
      return;
    }
    userService.update(user);
    responseJson(request, response, Result.success());
  }

  @DELETE
  @Path("/delete")
  public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String id = request.getParameter("id");
    long userId = Long.parseLong(id);
    userService.delete(userId);
    responseJson(request, response, Result.success());
  }

  @POST
  @Path("/login")
  public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String name = request.getParameter("name");
    String password = request.getParameter("password");
    User user = userService.queryUserByNameAndPassword(name, password);
    if (null != user) {
      String sessionId = request.getRequestedSessionId();
      request.getSession().setAttribute(sessionId, user);
      responseJson(request, response, Result.success());
    } else {
      responseJson(request, response, Result.error(WebError.LOGIN_ERR.getDescription()));
    }
  }

  private User getUserFromRequest(HttpServletRequest request) {

    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String phoneNumber = request.getParameter("phoneNumber");
    String password = request.getParameter("password");

    User user = new User();
    user.setPhoneNumber(phoneNumber);
    user.setName(name);
    user.setPassword(password);
    user.setEmail(email);
    return user;
  }

  private String validUser(User user) {
    Set<ConstraintViolation<User>> errs = validator.validate(user);
    StringBuilder sb = new StringBuilder();
    for (ConstraintViolation<User> err : errs) {
      sb.append(err.getMessage() + ",");
    }
    if (sb.length() > 1) {
      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

}
