package spring.guides.cache.service;

import spring.guides.cache.bean.Book;

/**
 * 图书仓库。
 *
 * @author dannong
 * @since 2017年05月30日 22:32
 */
public interface BookRepository {

    Book getByIsbn(String isbn);

}
