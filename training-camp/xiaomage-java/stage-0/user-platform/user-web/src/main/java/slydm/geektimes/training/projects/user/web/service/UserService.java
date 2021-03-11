package slydm.geektimes.training.projects.user.web.service;

import slydm.geektimes.training.projects.user.web.domin.User;

/**
 * 用户服务
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 0:52
 */
public interface UserService {

  /**
   * 用户列表
   *
   * @return 用户列表
   */
  Iterable<User> userList();


  /**
   * 注册用户
   *
   * @param user 用户对象
   * @return 成功返回<code>true</code>
   */
  boolean register(User user);

  /**
   * 注销用户
   *
   * @param user 用户对象
   * @return 成功返回<code>true</code>
   */
  boolean deregister(User user);

  /**
   * 更新用户信息
   *
   * @param user 用户对象
   */
  boolean update(User user);

  User queryUserById(Long id);

  User queryUserByNameAndPassword(String name, String password);

}
