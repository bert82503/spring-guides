package spring.guides.test.junit4;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 基于TestNG的集成上下文测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 09:04
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractJUnit4IntegrationContextTests
        extends AbstractJUnit4SpringContextTests {
    // 注解继承特性
}
