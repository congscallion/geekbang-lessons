package slydm.geektimes.training.projects.user.web.controller;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import slydm.geektimes.training.projects.user.domin.Book;
import slydm.geektimes.training.projects.user.repository.BookRepositoryImpl;
import slydm.geektimes.training.projects.user.service.BookService;
import slydm.geektimes.training.projects.user.service.BookServiceImpl;
import slydm.geektimes.training.web.mvc.controller.PageController;


/**
 * 书籍控制器
 *
 * @author wangcymy@gmail.com(wangcong) 3/4/21 10:12 PM
 */
@Path("/books")
public class BookController extends BaseController implements PageController {

  private final BookService bookService;

  public BookController() {
    this.bookService = new BookServiceImpl(new BookRepositoryImpl());
  }

  @GET
  @Path("")
  public String index(HttpServletRequest request, HttpServletResponse response) {

    this.findAll(request, response);
    return "book/list";
  }


  @GET
  @Path("list")
  public String list(HttpServletRequest request, HttpServletResponse response) {

    this.findAll(request, response);
    return "book/list";
  }

  @GET
  @Path("/view")
  public void view(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String id = request.getParameter("id");
    long bookId = Long.parseLong(id);
    Optional<Book> book = this.bookService.findById(bookId);
    responseJson(request, response, book.orElse(null));
  }

  private void findAll(HttpServletRequest request, HttpServletResponse response) {
    Iterable<Book> books = this.bookService.findAll();
    request.setAttribute("pageName", "Books");
    request.setAttribute("books", books);
  }

}
