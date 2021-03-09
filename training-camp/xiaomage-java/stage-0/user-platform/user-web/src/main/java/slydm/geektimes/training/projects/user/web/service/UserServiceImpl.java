package slydm.geektimes.training.projects.user.web.service;

import slydm.geektimes.training.projects.user.web.domin.User;
import slydm.geektimes.training.projects.user.web.repository.UserRepository;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 0:52
 */
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
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
  public boolean deregister(User user) {
    return userRepository.deleteById(user.getId());
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
    return userRepository.getByNameAndPassword(name, password)
        .orElseThrow(() -> new RuntimeException("用户:" + name + "," + password + "不存在."));
  }
}
