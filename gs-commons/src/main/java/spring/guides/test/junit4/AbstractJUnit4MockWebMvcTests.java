package spring.guides.test.junit4;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

/**
 * 基于{@link MockMvc}的{@link Controller @Controller}单元测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 21:54
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public abstract class AbstractJUnit4MockWebMvcTests {

    // @AutoConfigureMockMvc
    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private XxxService xxxService;


    protected ResultActions doGet(RequestBuilder requestBuilder)
            throws Exception {
        return mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

}
