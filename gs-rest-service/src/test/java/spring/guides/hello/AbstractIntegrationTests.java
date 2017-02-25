package spring.guides.hello;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 集成测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 21:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTests {

    // @AutoConfigureMockMvc
    @Resource
    protected MockMvc mockMvc;

}
