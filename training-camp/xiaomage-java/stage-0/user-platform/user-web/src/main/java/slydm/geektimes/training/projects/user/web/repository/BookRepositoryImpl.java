package slydm.geektimes.training.projects.user.web.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import slydm.geektimes.training.projects.user.web.domin.Book;

/**
 * 书籍数据源服务实例
 *
 * <p>
 * 本实现在内存中完成
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 15:59
 */
public class BookRepositoryImpl implements BookRepository {

  private static List<Book> bookList;

  static {
    Book book1 = new Book();
    book1.setId(1L);
    book1.setTitle("Beginning Spring Boot 2");
    book1.setIsbn("978-1-4842-2930-9");
    book1.setAuthor("K. Siva Prasad Reddy");
    book1.setYearPublished("2017");

    Book book2 = new Book();
    book2.setId(2L);
    book2.setTitle("Effective Java");
    book2.setEdition("Third Edition");
    book2.setIsbn("978-0-13-468599-1");
    book2.setAuthor("Joshua Block");
    book2.setYearPublished("2018");

    Book[] books = {book1, book2};
    bookList = Arrays.asList(books);
  }

  @Override
  public Iterable<Book> findAll() {
    return bookList;
  }

  @Override
  public Optional<Book> findById(long id) {
    return bookList.stream()
        .filter(book -> book.getId() == id)
        .findFirst();
  }

}
