package spring.guides.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 问候应用入口。
 *
 * @author dannong
 * @since 2017年02月22日 20:41
 */
@SpringBootApplication
public class HelloWorldConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldConfiguration.class, args);
    }

}
