package spring.guides.hello;

import spring.guides.test.junit4.AbstractJUnit4MockWebMvcTests;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Test of {@link GreetingController}.
 *
 * @author dannong
 * @since 2017年02月21日 23:59
 */
public class GreetingControllerTests extends AbstractJUnit4MockWebMvcTests {

    @Test
    public void noParam() throws Exception {
        this.doGet(get("/greeting"))
                .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

    @Test
    public void param() throws Exception {
        this.doGet(get("/greeting").param("name", "Spring Community"))
                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
    }

}
