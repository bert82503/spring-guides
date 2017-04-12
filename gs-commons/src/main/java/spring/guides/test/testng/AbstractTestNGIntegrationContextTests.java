package spring.guides.test.testng;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 基于TestNG的集成上下文测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 09:04
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractTestNGIntegrationContextTests
        extends AbstractTestNGSpringContextTests {
    // 注解继承特性
}
