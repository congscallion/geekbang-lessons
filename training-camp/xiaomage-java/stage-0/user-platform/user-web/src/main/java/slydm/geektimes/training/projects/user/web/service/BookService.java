package slydm.geektimes.training.projects.user.web.service;

import java.util.Optional;
import slydm.geektimes.training.projects.user.web.domin.Book;

public interface BookService {

  Iterable<Book> findAll();

  Optional<Book> findById(long id);
}
