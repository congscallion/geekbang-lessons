package slydm.geektimes.training.projects.user.web.service;

import slydm.geektimes.training.projects.user.web.domin.Book;

import java.util.Optional;

public interface BookService {

  Iterable<Book> findAll();

  Optional<Book> findById(long id);
}
