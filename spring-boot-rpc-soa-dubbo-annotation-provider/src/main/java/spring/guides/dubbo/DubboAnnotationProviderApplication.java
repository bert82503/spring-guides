package spring.guides.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

/**
 * Dubbo 注解服务提供者启动入口点。
 *
 * @author dannong
 * @since 2017年10月04日
 */
@SpringBootApplication(
    exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class}
)
@SuppressWarnings("startup-enter-point")
public class DubboAnnotationProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboAnnotationProviderApplication.class, args);
  }

}
