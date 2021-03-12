package slydm.geektimes.training.projects.user.service;

import slydm.geektimes.training.projects.user.domin.User;

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
   * @param userId 用户ID
   * @return 成功返回<code>true</code>
   */
  boolean delete(Long userId);

  /**
   * 更新用户信息
   *
   * @param user 用户对象
   */
  boolean update(User user);

  /**
   * 根据用户ID查询用户
   *
   * @param id 用户id
   * @return 与ID关联的用户
   */
  User queryUserById(Long id);

  /**
   * 根据用户名和密码查询用户信息
   *
   * @param name     用户名
   * @param password 密码
   * @return 匹配的单个用户
   */
  User queryUserByNameAndPassword(String name, String password);

}
