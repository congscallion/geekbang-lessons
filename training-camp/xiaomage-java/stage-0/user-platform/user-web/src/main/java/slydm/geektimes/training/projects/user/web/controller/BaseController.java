package slydm.geektimes.training.projects.user.web.controller;

import com.google.gson.Gson;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import slydm.geektimes.training.projects.user.web.Result;
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
      bookJson = gson.toJson(Result.error("data was not found."));
    }

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.print(bookJson);
    out.flush();
  }

}
