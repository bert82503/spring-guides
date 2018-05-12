package spring.guides.actuator.jmx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * JMX应用入口点。
 *
 * @author dannong
 * @since 2017年02月09日 11:27
 */
@SpringBootApplication
@SuppressWarnings("startup-enter-point")
public class JmxHttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmxHttpApplication.class, args);
    }

}
