package spring.guides.cache.bean;

/**
 * 图书。
 *
 * @author dannong
 * @since 2017年05月30日 22:20
 */
public class Book {

    /**
     * 国际标准书号(International Standard Book Number, ISBN)
     */
    private String isbn;
    /**
     * 标题/名称
     */
    private String title;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }
}
