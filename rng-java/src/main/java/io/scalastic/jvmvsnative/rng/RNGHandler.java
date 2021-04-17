package io.scalastic.jvmvsnative.rng;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RNGHandler {
  
  // Metric Counter to collect the amount of hash calls
  private Counter metricRNG;
  
  public RNGHandler(final MeterRegistry registry) {
    // Register metric Counter
    metricRNG = registry.counter("rng", "method", "generateRandom");
  }
  
  @Autowired
  private RNGService rngService;
  
  public Mono<ServerResponse> rng(ServerRequest request) {
  
    metricRNG.increment();
    
    return ServerResponse.ok()
        .contentType(MediaType.TEXT_PLAIN)
        .body(BodyInserters.fromValue(rngService.generateRandom(32)));
  }
}
