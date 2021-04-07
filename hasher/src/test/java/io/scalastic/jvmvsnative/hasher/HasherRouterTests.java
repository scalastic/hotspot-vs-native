package io.scalastic.jvmvsnative.hasher;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HasherRouterTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testHello() {

        /*String data = "scalastic";

        webTestClient.post()
                .uri("/")
                .body(Mono.just(data), String.class)
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
        */
    }
}