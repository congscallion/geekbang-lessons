package slydm.geektimes.training.projects.user.service;

import javax.annotation.Resource;
import slydm.geektimes.training.context.annotation.Component;
import slydm.geektimes.training.projects.user.domin.User;
import slydm.geektimes.training.projects.user.repository.UserRepository;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 0:52
 */
@Component("userService")
public class UserServiceImpl implements UserService {

  @Resource(name = "userRepository")
  private UserRepository userRepository;

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Iterable<User> userList() {
    return userRepository.getAll();
  }

  @Override
  public boolean register(User user) {
    return userRepository.save(user);
  }

  @Override
  public boolean delete(Long userId) {
    return userRepository.deleteById(userId);
  }

  @Override
  public boolean update(User user) {
    return userRepository.update(user);
  }

  @Override
  public User queryUserById(Long id) {
    return userRepository.getById(id).orElseThrow(() -> new RuntimeException("用户:" + id + "不存在."));
  }

  @Override
  public User queryUserByNameAndPassword(String name, String password) {
    return userRepository.getByNameAndPassword(name, password).orElse(null);
  }
}
