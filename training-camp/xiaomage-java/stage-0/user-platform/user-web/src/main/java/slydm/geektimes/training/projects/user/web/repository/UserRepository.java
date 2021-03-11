package slydm.geektimes.training.projects.user.web.repository;

import java.util.Optional;
import slydm.geektimes.training.projects.user.web.domin.User;

/**
 * 用户数据仓库服务
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:50
 */
public interface UserRepository {

  /**
   * 保存用户
   *
   * @param user 待保存信息
   * @return true:成功; else false;
   */
  boolean save(User user);

  /**
   * 根据用户ID删除用户
   *
   * @param userId 用户ID
   * @return true:成功; else false;
   */
  boolean deleteById(Long userId);

  /**
   * 更新用户信息全量更新
   *
   * @param user 待更新信息
   * @return true:成功; else false;
   */
  boolean update(User user);

  /**
   * 根据用户ID查询用户信息
   *
   * @param userId 用户ID
   * @return 与ID相关联的用户信息
   */
  Optional<User> getById(Long userId);

  /**
   * 根据用户名和密码查询用户信息
   *
   * @param userName 用户名
   * @param password 密码
   * @return 匹配的单个用户
   */
  Optional<User> getByNameAndPassword(String userName, String password);

  /**
   * 所有用户列表
   *
   * @return 所有用户列表
   */
  Iterable<User> getAll();

}
