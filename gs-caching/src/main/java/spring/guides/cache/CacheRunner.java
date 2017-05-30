package spring.guides.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import spring.guides.cache.service.BookRepository;

/**
 * 2. Using the repository
 *
 * @author dannong
 * @since 2017年05月30日 22:45
 */
@Component("cacheRunner")
public class CacheRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CacheRunner.class);

    @Autowired
    private BookRepository bookRepository;


    @Override
    public void run(String... args) throws Exception {
        logger.info(".... Fetching books");
        logger.info("isbn-1234 --> {}", bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 --> {}", bookRepository.getByIsbn("isbn-4567"));
        // 看日志输出耗时
        logger.info("isbn-1234 --> {}", bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 --> {}", bookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 --> {}", bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 --> {}", bookRepository.getByIsbn("isbn-1234"));
    }

}
