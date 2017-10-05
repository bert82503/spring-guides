package spring.guides.dubbo;

import io.dubbo.springboot.DubboAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * Dubbo XML 服务消费者入口点。
 *
 * @author dannong
 * @since 2017年04月05日 22:22
 */
@SpringBootApplication(
    exclude = {DubboAutoConfiguration.class,
        DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class}
)
@ImportResource({"classpath*:META-INF/spring/*.xml"})
@SuppressWarnings("startup-enter-point")
public class DubboXmlConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboXmlConsumerApplication.class, args);
  }

}
