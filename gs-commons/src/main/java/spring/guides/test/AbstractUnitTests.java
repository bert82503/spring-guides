package spring.guides.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

/**
 * 单元测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 21:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractUnitTests {

    // @AutoConfigureMockMvc
    @Resource
    protected MockMvc mockMvc;


    protected ResultActions doGet(RequestBuilder requestBuilder)
            throws Exception {
        return mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

}
