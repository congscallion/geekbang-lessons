package slydm.geektimes.training.projects.user.domin;

import java.io.Serializable;

/**
 * 书籍 domain
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 15:59
 */
public class Book implements Serializable {

  private long id;
  private String title;
  private String edition;
  private String isbn;
  private String author;
  private String yearPublished;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getEdition() {
    return edition;
  }

  public void setEdition(String edition) {
    this.edition = edition;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getYearPublished() {
    return yearPublished;
  }

  public void setYearPublished(String yearPublished) {
    this.yearPublished = yearPublished;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", edition='" + edition + '\'' +
        ", isbn='" + isbn + '\'' +
        ", author='" + author + '\'' +
        ", yearPublished='" + yearPublished + '\'' +
        '}';
  }
}
