package slydm.geektimes.training.projects.user.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.Optional;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slydm.geektimes.training.projects.user.web.domin.Book;
import slydm.geektimes.training.projects.user.web.repository.BookRepositoryImpl;
import slydm.geektimes.training.projects.user.web.service.BookService;
import slydm.geektimes.training.projects.user.web.service.BookServiceImpl;
import slydm.geektimes.training.projects.user.web.util.Error;
import slydm.geektimes.training.projects.user.web.util.ErrorResponse;
import slydm.geektimes.training.web.mvc.controller.PageController;


/**
 * 书籍控制器
 *
 * @author wangcymy@gmail.com(wangcong) 3/4/21 10:12 PM
 */
@Path("/books")
public class BookController implements PageController {

  private static final long serialVersionUID = -8199839431714257029L;
  private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
  private final BookService bookService;

  public BookController() {
    this.bookService = new BookServiceImpl(new BookRepositoryImpl());
  }

  @GET
  @Path("")
  public String index(HttpServletRequest request, HttpServletResponse response) {
    try {
      this.findAll(request, response);
    } catch (Exception exception) {
      LOGGER.error(exception.getMessage());
    }
    return "book/list";
  }


  @GET
  @Path("list")
  public String list(HttpServletRequest request, HttpServletResponse response) {
    try {
      this.findAll(request, response);
    } catch (Exception exception) {
      LOGGER.error(exception.getMessage());
    }
    return "book/list";
  }

  @GET
  @Path("/view")
  public void view(HttpServletRequest request, HttpServletResponse response) {
    try {
      this.findById(request, response);
    } catch (Exception exception) {
      LOGGER.error(exception.getMessage());
    }
  }

  private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Iterable<Book> books = this.bookService.findAll();
    request.setAttribute("pageName", "Books");
    request.setAttribute("books", books);
  }

  private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    long bookId = Long.parseLong(id);
    Optional<Book> book = this.bookService.findById(bookId);
    String bookJson;
    try (Jsonb jsonbObject = JsonbBuilder.create()) {
      if (book.isPresent()) {
        response.setStatus(200);
        bookJson = jsonbObject.toJson(book.get());
      } else {
        response.setStatus(404);
        ErrorResponse errorResponse = new ErrorResponse();
        Error error = new Error();
        error.setMessage("Book with ID " + id + " was not found.");
        error.setCreatedAt(ZonedDateTime.now());
        errorResponse.setError(error);
        bookJson = jsonbObject.toJson(error);
      }
    }
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.print(bookJson);
    out.flush();
  }
}
