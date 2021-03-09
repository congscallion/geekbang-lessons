package slydm.geektimes.training.projects.user.web.repository;

import java.util.Optional;
import slydm.geektimes.training.projects.user.web.domin.User;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:50
 */
public interface UserRepository {

  boolean save(User user);

  boolean deleteById(Long userId);

  boolean update(User user);

  Optional<User> getById(Long userId);

  Optional<User> getByNameAndPassword(String userName, String password);

  Iterable<User> getAll();

}
