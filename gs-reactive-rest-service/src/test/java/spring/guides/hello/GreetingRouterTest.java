package spring.guides.hello;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.annotation.Resource;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingRouterTest {

	/**
	 * Spring Boot will create a `WebTestClient` for you,
	 * already configure and ready to issue requests against "localhost:RANDOM_PORT"
	 */
	@Resource
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	void hello() {
		webTestClient.get()
				.uri("/hello")
				.accept(MediaType.TEXT_PLAIN)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("Hello, Spring!");
	}
}
