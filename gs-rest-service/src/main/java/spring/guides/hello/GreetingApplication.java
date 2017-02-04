package spring.guides.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 问候应用入口点。
 *
 * Make the application executable.
 *
 * @author dannong
 * @since 2017年01月30日 09:47
 */
@SpringBootApplication // same as @SpringBootConfiguration/@Configuration @EnableAutoConfiguration @ComponentScan
public class GreetingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreetingApplication.class, args);
    }

}
