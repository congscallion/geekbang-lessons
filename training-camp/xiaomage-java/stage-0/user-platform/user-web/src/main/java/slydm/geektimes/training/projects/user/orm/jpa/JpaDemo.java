package slydm.geektimes.training.projects.user.orm.jpa;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import slydm.geektimes.training.projects.user.domin.User;

public class JpaDemo {

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("emf", getProperties());
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    User user = new User();
    user.setName("小马哥");
    user.setPassword("******");
    user.setEmail("mercyblitz@gmail.com");
    user.setPhoneNumber("123456789");
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user);
    transaction.commit();

    System.out.println(entityManager.find(User.class, 1L));
  }

  private static Map<String, Object> getProperties() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
    properties.put("hibernate.id.new_generator_mappings", false);
    properties.put("hibernate.connection.datasource", getDataSource());
    return properties;
  }

  private static DataSource getDataSource() {
    EmbeddedDataSource dataSource = new EmbeddedDataSource();
    dataSource.setDatabaseName("/db/user-platform");
    dataSource.setCreateDatabase("create");
    return dataSource;
  }
}
