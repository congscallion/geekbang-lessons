package slydm.geektimes.training.projects.user.web.repository;

import java.util.Optional;
import slydm.geektimes.training.projects.user.web.domin.Book;

public interface BookRepository {

  Iterable<Book> findAll();

  Optional<Book> findById(long id);
}
