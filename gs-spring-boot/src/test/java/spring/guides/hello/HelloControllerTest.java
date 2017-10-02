package spring.guides.hello;

import spring.guides.test.junit.AbstractJUnitMockWebMvcTests;

import org.springframework.http.MediaType;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
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
public class HelloControllerTest extends AbstractJUnitMockWebMvcTests {

  @Test
  public void index() throws Exception {
    doGet(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
  }

}
