package spring.guides.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 应用程序入口点。
 *
 * <p>3. Create an Application class (创建应用入口类).
 *
 * @author dannong
 * @since 2017年03月05日 19:11
 */
@SpringBootApplication // = @SpringBootConfiguration/@Configuration + @EnableAutoConfiguration + @ComponentScan
@SuppressWarnings("enter-point")
public class HelloApplication {

  private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

  /**
   * 4. Run the application (运行应用程序).
   */
  public static void main(String[] args) {
    SpringApplication.run(HelloApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    // Lambda Expressions (The Java Tutorials > Learning the Java Language > Classes and Objects)
    // http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
    return args -> {
      logger.info("Let's inspect the beans provided by Spring Boot:");
      // 杜绝在生产中使用
//      System.out.println("Let's inspect the beans provided by Spring Boot:");

      String[] beanNames = ctx.getBeanDefinitionNames();
//      Arrays.sort(beanNames);
      for (String beanName : beanNames) {
        logger.info(beanName);
      }
    };
  }

}
