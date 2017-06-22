package spring.guides.hello;

import spring.guides.test.junit4.AbstractJUnit4MockWebMvcTests;

import org.springframework.http.MediaType;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Unit test of {@link HelloController}.
 *
 * <p>5. Add Unit Tests (增加单元测试).
 *
 * @author dannong
 * @since 2017年03月06日 00:07
 */
public class HelloControllerTest extends AbstractJUnit4MockWebMvcTests {

    @Test
    public void index() throws Exception {
        this.doGet(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("Greetings from Spring Boot!"));
    }

}
