package spring.guides.hello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;

import spring.guides.test.AbstractUnitTests;

/**
 * Test of {@link GreetingController}.
 *
 * @author dannong
 * @since 2017年02月21日 23:59
 */
public class GreetingControllerTests extends AbstractUnitTests {

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
