package slydm.geektimes.training.projects.user.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/9 18:19
 */
@WebServlet(name = "hello", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
  }
}
