package spring.guides.hello.web;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.springframework.http.MediaType;

import spring.guides.test.junit.AbstractJUnitMockWebMvcTests;

/**
 * Unit test of {@link HomeController}.
 *
 * <p>5. Add Unit Tests (增加单元测试).
 *
 * @since 2017年03月06日
 */
public class HomeControllerTest extends AbstractJUnitMockWebMvcTests {

  @Test
  public void index() throws Exception {
    doGet(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
  }

}
