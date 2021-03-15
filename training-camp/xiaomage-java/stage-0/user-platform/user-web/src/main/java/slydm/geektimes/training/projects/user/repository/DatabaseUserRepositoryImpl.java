package slydm.geektimes.training.projects.user.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import slydm.geektimes.training.projects.user.domin.User;
import slydm.geektimes.training.projects.user.function.ThrowableFunction;
import slydm.geektimes.training.projects.user.util.DbUtil;

/**
 * 用户数据仓库服务实现
 * <p>
 * 本实现在数据库中完成 数据库能力依赖于 {@link }
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:52
 * @see
 */
public class DatabaseUserRepositoryImpl implements UserRepository {

  private static Logger logger = Logger.getLogger(DatabaseUserRepositoryImpl.class.getName());

  public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES " +
      "(?,?,?,?)";

  public static final String DELETE_USER_BY_ID_DML_SQL = "delete table from users where id=?";

  public static final String UPDATE_USER_DML_SQL = "update users set name=?,password=?,email=?,phoneNumber=? where id=?";
  public static final String SELECT_USER_BY_ID_SQL = "select * from users where id=?";
  public static final String SELECT_USER_BY_NAME_AND_PWD_SQL = "select * from users where name=? and password=?";
  public static final String SELECT_ALL_SQL = "select * from users";

  /**
   * 通用的将数据库结果集转换为单个用户
   */
  public static final ThrowableFunction<ResultSet, User> TO_USER = result -> {
    if (result.next()) {
      User user = new User();
      user.setId(result.getLong("id"));
      user.setName(result.getString("name"));
      user.setPassword(result.getString("password"));
      user.setEmail(result.getString("email"));
      user.setPhoneNumber(result.getString("phoneNumber"));
      return user;
    }
    return null;
  };

  /**
   * 通用的将数据库结果集转换为用户列表
   */
  public static final ThrowableFunction<ResultSet, List<User>> TO_USER_LIST = result -> {
    List<User> list = new ArrayList<>();
    while (result.next()) {
      User user = new User();
      user.setId(result.getLong("id"));
      user.setName(result.getString("name"));
      user.setPassword(result.getString("password"));
      user.setEmail(result.getString("email"));
      user.setPhoneNumber(result.getString("phoneNumber"));
      list.add(user);
    }
    return list;
  };


  @Override
  public boolean save(User user) {

    int i = DbUtil.executeUpdate(
        INSERT_USER_DML_SQL,
        DbUtil.COMMON_EXCEPTION_HANDLER,
        user.getName(), user.getPassword(), user.getEmail(), user.getPhoneNumber());

    return i == 1;
  }

  @Override
  public boolean deleteById(Long userId) {

    int i = DbUtil.executeUpdate(
        DELETE_USER_BY_ID_DML_SQL,
        DbUtil.COMMON_EXCEPTION_HANDLER,
        userId);
    return i == 1;
  }

  @Override
  public boolean update(User user) {
    int i = DbUtil.executeUpdate(
        UPDATE_USER_DML_SQL,
        DbUtil.COMMON_EXCEPTION_HANDLER,
        user.getName(), user.getPassword(), user.getEmail(), user.getPhoneNumber(), user.getId());
    return i == 1;
  }

  @Override
  public Optional<User> getById(Long userId) {

    User re = DbUtil.executeQuery(
        SELECT_USER_BY_ID_SQL,
        TO_USER,
        DbUtil.COMMON_EXCEPTION_HANDLER,
        userId);
    return re == null ? Optional.empty() : Optional.of(re);
  }

  @Override
  public Optional<User> getByNameAndPassword(String userName, String password) {

    User re = DbUtil.executeQuery(
        SELECT_USER_BY_NAME_AND_PWD_SQL,
        TO_USER,
        DbUtil.COMMON_EXCEPTION_HANDLER,
        userName, password);
    return re == null ? Optional.empty() : Optional.of(re);
  }

  @Override
  public Iterable<User> getAll() {

    List<User> users = DbUtil.executeQuery(
        SELECT_ALL_SQL,
        TO_USER_LIST,
        DbUtil.COMMON_EXCEPTION_HANDLER);
    return users;
  }
}
