package spring.guides.cache.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import spring.guides.cache.bean.Book;
import spring.guides.cache.service.BookRepository;

/**
 * 1. Create a book repository
 *
 * @author dannong
 * @since 2017年05月30日 22:35
 */
@CacheConfig(cacheNames = "books")
@Service(value = "bookRepository")
public class SimpleBookRepository implements BookRepository {

    @Cacheable(key = "#isbn") // 3. Enable caching
    @Override
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "Some book");
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
