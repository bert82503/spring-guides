package spring.guides.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Caching Data with Spring.
 *
 * @author dannong
 * @since 2017年05月30日 22:19
 */
@SpringBootApplication
@EnableCaching // 3. Enable caching (cache store: ConcurrentHashMap)
@SuppressWarnings("startup-enter-point")
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

}
