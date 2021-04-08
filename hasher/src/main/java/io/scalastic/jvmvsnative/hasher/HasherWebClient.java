package io.scalastic.jvmvsnative.hasher;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class HasherWebClient {
  
  private final WebClient client = WebClient.create("http://localhost:8080");
  
  public void getResult() {
    
    client.post()
        .uri("/")
        .accept(MediaType.APPLICATION_OCTET_STREAM)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .bodyValue("123456789")
        .exchange()
        .flatMap(res -> res.bodyToMono(String.class))
        .subscribe(res -> System.out.println("Hasher is up and functional!"));
    
  }
  
}
