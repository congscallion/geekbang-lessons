package slydm.geektimes.training.projects.user.web.repository;

import java.util.Optional;
import slydm.geektimes.training.projects.user.web.domin.Book;

/**
 * 书籍仓库服务接口
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 21:52
 */
public interface BookRepository {

  /**
   * 查询所有书籍
   *
   * @return 书籍列表
   */
  Iterable<Book> findAll();

  /**
   * 根据id查询书籍
   *
   * @param id 书籍ID
   * @return 特定ID的书籍
   */
  Optional<Book> findById(long id);
}
