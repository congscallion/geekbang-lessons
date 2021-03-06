package slydm.geektimes.training.projects.user.web.repository;

import slydm.geektimes.training.projects.user.web.domin.Book;

import java.util.Optional;

public interface BookRepository {

  Iterable<Book> findAll();

  Optional<Book> findById(long id);
}
