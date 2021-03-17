package slydm.geektimes.training.projects.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slydm.geektimes.training.projects.user.domin.User;

/**
 * 用户数据仓库服务实现
 * <p>
 * 本实现在数据库中完成 数据库能力依赖于 {@link }
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:52
 * @see
 */
public class JpaUserRepositoryImpl implements UserRepository {

  private static Logger logger = LoggerFactory.getLogger(JpaUserRepositoryImpl.class);

  @Resource(name = "bean/EntityManager")
  private EntityManager entityManager;

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public boolean save(User user) {
    executeInTransaction(user, usr -> entityManager.persist(usr));
    return true;
  }

  @Override
  public boolean deleteById(Long userId) {
    Optional<User> byId = getById(userId);
    if (byId.isPresent()) {
      executeInTransaction(byId.get(), usr -> entityManager.remove(usr));
    }
    return true;
  }

  @Override
  public boolean update(User user) {
    executeInTransaction(user, usr -> {
      Optional<User> byId = getById(usr.getId());
      if (byId.isPresent()) {
        User old = byId.get();
        old.setEmail(usr.getEmail());
        old.setName(usr.getName());
        old.setPassword(usr.getPassword());
        old.setPhoneNumber(usr.getPhoneNumber());
        entityManager.merge(old);
      } else {
        entityManager.persist(usr);
      }
    });
    return true;
  }

  @Override
  public Optional<User> getById(Long userId) {
    return Optional.of(entityManager.find(User.class, userId));
  }

  @Override
  public Optional<User> getByNameAndPassword(String userName, String password) {
    List<User> resultList = entityManager
        .createQuery("from User u where u.name=:name and u.password=:password", User.class)
        .setParameter("name", userName)
        .setParameter("password", password)
        .getResultList();
    return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
  }

  @Override
  public Iterable<User> getAll() {

    List<User> users = entityManager.createQuery("from User", User.class).getResultList();
    return users;
  }

  private void executeInTransaction(User user, Consumer<User> consumer) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      consumer.accept(user);
      transaction.commit();
    } catch (Throwable e) {
      logger.error("jpa error:", e);
      transaction.rollback();
    }
  }

}
