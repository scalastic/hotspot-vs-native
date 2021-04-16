package io.scalastic.jvmvsnative.hasher;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HasherHandler {
  
  // Metric Counter to collect the amount of hash calls
  private Counter metricHasher;
  
  public HasherHandler(final MeterRegistry registry) {
    // Register metric Counter
    metricHasher = registry.counter("hasher", "method", "hash");
  }
  
  @Autowired
  private HasherService hasherService;
  
  public Mono<ServerResponse> generate(ServerRequest request) {
    
    metricHasher.increment();
    return request.bodyToMono(String.class)
        .flatMap(data -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .bodyValue(hasherService.hash(data))
        );
  }
  
  
}
