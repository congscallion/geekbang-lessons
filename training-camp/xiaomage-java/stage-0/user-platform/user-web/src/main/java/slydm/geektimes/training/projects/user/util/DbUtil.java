package slydm.geektimes.training.projects.user.util;

import static org.apache.commons.lang.ClassUtils.wrapperToPrimitive;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slydm.geektimes.training.projects.context.ComponentContext;
import slydm.geektimes.training.projects.user.function.ThrowableFunction;

/**
 * 数据库工具类，该类负责数据库连接管理并且提供基础的数据库操作能力
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:58
 */
public class DbUtil {

  private static final Logger logger = LoggerFactory.getLogger(DbUtil.class.getName());

  /**
   * 通用处理方式
   */
  public static Consumer<Throwable> COMMON_EXCEPTION_HANDLER = e -> logger.error(e.getMessage());


  private static Driver initDriver() throws Exception {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    Driver driver = DriverManager.getDriver("jdbc:derby:/db/user-platform;create=true");
    return driver;
  }


  public static Connection getConnection() {
    return ComponentContext.getInstance().getConnection();
  }


  /**
   * @param sql      sql 语句
   * @param function 数据转换函数
   * @return 数据转换后的对象
   */
  public static <T> T executeQuery(String sql, ThrowableFunction<ResultSet, T> function,
      Consumer<Throwable> exceptionHandler, Object... args) {
    try (Connection conn = getConnection();
        PreparedStatement statement = createStatement(conn, sql, args);
        ResultSet resultSet = statement.executeQuery()) {

      // 返回一个 POJO List -> ResultSet -> POJO List
      // ResultSet -> T
      return function.apply(resultSet);
    } catch (Throwable e) {
      exceptionHandler.accept(e);
      return null;
    }
  }


  /**
   * @param sql sql 语句
   * @return 数据转换后的对象
   */
  public static int executeUpdate(String sql, Consumer<Throwable> exceptionHandler, Object... args) {
    try (Connection conn = getConnection();
        PreparedStatement statement = createStatement(conn, sql, args)) {

      return statement.executeUpdate();
    } catch (Throwable e) {
      exceptionHandler.accept(e);
      return 0;
    }
  }

  private static PreparedStatement createStatement(Connection conn, String sql, Object... args) throws Exception {
    PreparedStatement preparedStatement = conn.prepareStatement(sql);
    for (int i = 0; i < args.length; i++) {
      Object arg = args[i];
      Class argType = arg.getClass();

      Class wrapperType = wrapperToPrimitive(argType);

      if (wrapperType == null) {
        wrapperType = argType;
      }

      // Boolean -> boolean
      String methodName = preparedStatementMethodMappings.get(argType);
      Method method = PreparedStatement.class.getMethod(methodName, int.class, wrapperType);
      method.invoke(preparedStatement, i + 1, args[i]);
    }
    return preparedStatement;
  }


  /**
   * 数据类型与 ResultSet 方法名映射
   */
  static Map<Class, String> resultSetMethodMappings = new HashMap<>();

  static Map<Class, String> preparedStatementMethodMappings = new HashMap<>();

  static {
    resultSetMethodMappings.put(Long.class, "getLong");
    resultSetMethodMappings.put(String.class, "getString");

    preparedStatementMethodMappings.put(Long.class, "setLong");
    preparedStatementMethodMappings.put(String.class, "setString");


  }

  public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users";

  public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
      "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
      "name VARCHAR(16) NOT NULL, " +
      "password VARCHAR(64) NOT NULL, " +
      "email VARCHAR(64) NOT NULL, " +
      "phoneNumber VARCHAR(64) NOT NULL" +
      ")";

  public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES " +
      "('A','******','a@gmail.com','1') , " +
      "('B','******','b@gmail.com','2') , " +
      "('C','******','c@gmail.com','3') , " +
      "('D','******','d@gmail.com','4') , " +
      "('E','******','e@gmail.com','5')";
//
//  public static void main(String[] args) throws Exception {
//
//    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//    Driver driver = DriverManager.getDriver("jdbc:derby:/db/user-platform;create=true");
//    Connection connection = driver.connect("jdbc:derby:/db/user-platform;create=true", new Properties());
//
//    Statement statement = connection.createStatement();
//    // 删除 users 表
//    try {
//      System.out.println(statement.execute(DROP_USERS_TABLE_DDL_SQL));
//    } catch (Exception ex) {
//      // 避免表不存在时报错
//    }
//    // 创建 users 表
//    System.out.println(statement.execute(CREATE_USERS_TABLE_DDL_SQL));
//    System.out.println(statement.executeUpdate(INSERT_USER_DML_SQL));
//
//    // 执行查询语句（DML）
//    ResultSet resultSet = statement.executeQuery("SELECT id,name,password,email,phoneNumber FROM users");
//    while (resultSet.next()) {
//
//      User user = new User();
//      user.setId(resultSet.getLong("id"));
//      user.setName(resultSet.getString("name"));
//      user.setPassword(resultSet.getString("password"));
//      user.setEmail(resultSet.getString("email"));
//      user.setPhoneNumber(resultSet.getString("phoneNumber"));
//
//      System.out.println(user);
//    }
//
//  }

}
