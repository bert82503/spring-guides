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
@SuppressWarnings("startup-enter-point")
public class HelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

}
