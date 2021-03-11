package slydm.geektimes.training.projects.user.service;

import java.util.Optional;
import slydm.geektimes.training.projects.user.domin.Book;
import slydm.geektimes.training.projects.user.repository.BookRepository;


/**
 * 书籍服务
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:50
 */
public class BookServiceImpl implements BookService {

  private BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public Iterable<Book> findAll() {
    return this.bookRepository.findAll();
  }

  @Override
  public Optional<Book> findById(long id) {
    return this.bookRepository.findById(id);
  }

}
