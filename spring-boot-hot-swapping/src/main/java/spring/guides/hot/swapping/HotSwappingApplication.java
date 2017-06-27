package spring.guides.hot.swapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 热插拔技术实验室。
 *
 * @author dannong
 * @since 2017年03月09日 17:27
 */
@SpringBootApplication
public class HotSwappingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotSwappingApplication.class, args);
    }

}
