package spring.guides.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 问候应用入口点。
 *
 * <p>Make the application executable.
 *
 * @author dannong
 * @since 2017年01月30日 09:47
 */
@SpringBootApplication // = @SpringBootConfiguration/@Configuration + @EnableAutoConfiguration + @ComponentScan
@SuppressWarnings("startup-enter-point")
public class GreetingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreetingApplication.class, args);
    }

}
