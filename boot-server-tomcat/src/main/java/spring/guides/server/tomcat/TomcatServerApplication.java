package spring.guides.server.tomcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Tomcat服务器配置示例。
 *
 * @author dannong
 * @since 2017年04月14日 23:35
 */
@SpringBootApplication
@SuppressWarnings("startup-enter-point")
public class TomcatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TomcatServerApplication.class, args);
    }

}
