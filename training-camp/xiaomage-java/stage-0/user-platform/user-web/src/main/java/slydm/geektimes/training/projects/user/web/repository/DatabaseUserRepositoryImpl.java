package slydm.geektimes.training.projects.user.web.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import slydm.geektimes.training.projects.user.web.domin.User;
import slydm.geektimes.training.projects.user.web.sql.DbUtil;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:52
 */
public class DatabaseUserRepositoryImpl implements UserRepository {

  private static Logger logger = Logger.getLogger(DatabaseUserRepositoryImpl.class.getName());

  public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES " +
      "(?,?,?,?)";

  public static final String DELETE_USER_BY_ID_DML_SQL = "delete table from users where id=?";

  public static final String UPDATE_USER_DML_SQL = "update users set name=?,password=?,email=?,phoneNumber=? where id=?";
  public static final String SELECT_USER_BY_ID_SQL = "select * from users where id=?";
  public static final String SELECT_USER_BY_NAME_AND_PWD_SQL = "select * from users where name=? and password=?";
  public static final String SELECT_ALL_SQL = "select * from users where name=? and password=?";


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
        result -> {
          User user = new User();
          user.setId(result.getLong("id"));
          user.setEmail(result.getString("name"));
          user.setEmail(result.getString("password"));
          user.setEmail(result.getString("email"));
          user.setEmail(result.getString("phoneNumber"));
          return user;
        },
        DbUtil.COMMON_EXCEPTION_HANDLER,
        userId);
    return Optional.of(re);
  }

  @Override
  public Optional<User> getByNameAndPassword(String userName, String password) {

    User re = DbUtil.executeQuery(
        SELECT_USER_BY_NAME_AND_PWD_SQL,
        result -> {
          User user = new User();
          user.setId(result.getLong("id"));
          user.setEmail(result.getString("name"));
          user.setEmail(result.getString("password"));
          user.setEmail(result.getString("email"));
          user.setEmail(result.getString("phoneNumber"));
          return user;
        },
        DbUtil.COMMON_EXCEPTION_HANDLER,
        userName, password);
    return Optional.of(re);
  }

  @Override
  public Iterable<User> getAll() {

    List<User> users = DbUtil.executeQuery(
        SELECT_ALL_SQL,
        result -> {
          List<User> tempList = new ArrayList<>();
          while (result.next()) {
            User user = new User();
            user.setId(result.getLong("id"));
            user.setEmail(result.getString("name"));
            user.setEmail(result.getString("password"));
            user.setEmail(result.getString("email"));
            user.setEmail(result.getString("phoneNumber"));
            tempList.add(user);
          }
          return tempList;
        },
        DbUtil.COMMON_EXCEPTION_HANDLER);

    return users;
  }
}
