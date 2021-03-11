package slydm.geektimes.training.projects.user.web.service;

import java.util.Optional;
import slydm.geektimes.training.projects.user.web.domin.Book;

/**
 * 书籍服务
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:50
 */
public interface BookService {

  /**
   * 查询所有书籍
   *
   * @return 书籍列表
   */
  Iterable<Book> findAll();

  /**
   * 根据ID查询书籍
   *
   * @param id 书籍ID
   * @return 与ID关联的书籍
   */
  Optional<Book> findById(long id);
}
