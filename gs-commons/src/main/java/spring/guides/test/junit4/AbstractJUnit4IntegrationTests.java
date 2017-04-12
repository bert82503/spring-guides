package spring.guides.test.junit4;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 基于TestNG的集成测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 09:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractJUnit4IntegrationTests {
    // 注解继承特性
}
