package io.scalastic.jvmvsnative.rng;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class RNGWebClient {
  
  private final WebClient client = WebClient.create("http://localhost:8080");
  
  public void getResult() {
    
    client.get()
        .uri("/")
        .accept(MediaType.TEXT_PLAIN)
        .exchange()
        .flatMap(res -> res.bodyToMono(String.class))
        .subscribe(res -> System.out.println("Rng is up and functional!"));
    
  }
}
