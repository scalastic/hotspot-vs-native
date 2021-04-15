package io.scalastic.jvmvsnative.hasher;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HasherRouterTests {
  
  @Autowired
  private WebTestClient webClient;
  
  @Test
  public void router() {
  
    webClient.post()
        .uri("/")
        .accept(MediaType.APPLICATION_OCTET_STREAM)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .bodyValue("123456789")
        .exchange()
        // dedicated DSL to test assertions against the response
        .expectStatus().isOk();
        
  }
}