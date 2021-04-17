package spring.guides.hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * For reactive applications, Spring offers the WebClient class, which is non-blocking.
 * We use a WebClient implementation to consume our RESTful service.
 * <p>
 * The Spring MVC RestTemplate class is, by nature, blocking.
 * Consequently, we do not want to use it in a reactive application.
 *
 * @author guangyi
 * @since 2021-04-18
 */
public class GreetingWebClient {

    public String getResult() {
        WebClient client = WebClient.create("http://localhost:8080");
        Mono<ClientResponse> result = client.get()
                .uri("/hello")
                .accept(MediaType.TEXT_PLAIN)
                .exchange();
//                .exchangeToMono(clientResponse -> {
//                    HttpStatus httpStatus = clientResponse.statusCode();
//                    if (httpStatus.equals(HttpStatus.OK)) {
//                        return clientResponse.bodyToMono(ClientResponse.class);
//                    }
//                    return Mono.error(new RuntimeException("request /hello error"));
////                    else if (httpStatus.is4xxClientError()) {
////                        return clientResponse.bodyToMono(ErrorContainer.class);
////                    } else {
////                        return Mono.error(clientResponse.createException());
////                    }
//                });

        return ">> result = " + result.flatMap(clientResponse ->
                clientResponse.bodyToMono(String.class))
                .blockOptional(Duration.ofSeconds(1L))
                .orElse("");
        // >> result = Hello, Spring!
    }
}
