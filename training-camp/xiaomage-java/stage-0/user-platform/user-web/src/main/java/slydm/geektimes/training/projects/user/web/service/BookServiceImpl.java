package slydm.geektimes.training.projects.user.web.service;

import slydm.geektimes.training.projects.user.web.domin.Book;
import slydm.geektimes.training.projects.user.web.repository.BookRepository;

import java.util.Optional;

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
