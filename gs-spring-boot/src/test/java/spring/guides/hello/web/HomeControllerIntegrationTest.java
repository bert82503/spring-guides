package spring.guides.hello.web;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import spring.guides.test.junit.AbstractJUnitHttpRestTemplateTests;

/**
 * Integration test of {@link HomeController}.
 *
 * @since 2017年10月02日
 */
public class HomeControllerIntegrationTest extends AbstractJUnitHttpRestTemplateTests {

  @LocalServerPort
  private int port;

  private URL base;


  @Before
  public void setUp() throws Exception {
    this.base = new URL("http://localhost:" + port + '/');
  }

  @Test
  public void index() {
    ResponseEntity<String> responseEntity = getForEntity(base.toString(), String.class);
    assertResponseStatusCode(responseEntity, HttpStatus.OK);
    assertResponseBody(responseEntity, "Greetings from Spring Boot!");
  }

}
