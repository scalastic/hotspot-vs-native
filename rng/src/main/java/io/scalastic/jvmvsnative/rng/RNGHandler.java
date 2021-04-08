package io.scalastic.jvmvsnative.rng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RNGHandler {
  
  @Autowired
  private RNGService rngService;
  
  public Mono<ServerResponse> rng(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.TEXT_PLAIN)
        .body(BodyInserters.fromValue(rngService.generateRandom(32)));
  }
}
