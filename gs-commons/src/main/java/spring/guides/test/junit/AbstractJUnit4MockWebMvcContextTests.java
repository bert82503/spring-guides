package spring.guides.test.junit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 基于{@link MockMvc}的{@link Controller @Controller}单元测试抽象基类。
 *
 * @author dannong
 * @since 2017年02月25日 21:54
 */
@WebMvcTest
public abstract class AbstractJUnit4MockWebMvcContextTests
        extends AbstractJUnit4SpringContextTests {

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
