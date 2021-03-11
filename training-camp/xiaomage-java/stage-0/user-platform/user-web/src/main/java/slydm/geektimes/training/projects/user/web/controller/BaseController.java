package slydm.geektimes.training.projects.user.web.controller;

import com.google.gson.Gson;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import slydm.geektimes.training.projects.user.web.util.Error;
import slydm.geektimes.training.projects.user.web.util.ErrorResponse;
import slydm.geektimes.training.web.mvc.controller.PageController;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/11 10:36
 */
public class BaseController implements PageController {

  protected void responseJson(HttpServletRequest request, HttpServletResponse response, Object data) throws Exception {

    String bookJson;
    Gson gson = new Gson();
    if (null != data) {
      response.setStatus(200);
      bookJson = gson.toJson(data);
    } else {
      response.setStatus(404);
      ErrorResponse errorResponse = new ErrorResponse();
      Error error = new Error();
      error.setMessage("data was not found.");
      error.setCreatedAt(ZonedDateTime.now());
      errorResponse.setError(error);
      bookJson = gson.toJson(error);
    }

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.print(bookJson);
    out.flush();
  }

}
