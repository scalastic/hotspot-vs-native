package io.scalastic.jvmvsnative.hasher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HasherRouter {
  
  @Bean
  public RouterFunction<ServerResponse> route(HasherHandler hasherHandler) {
    
    return RouterFunctions.route()
        .POST("/",
            RequestPredicates.contentType(MediaType.APPLICATION_OCTET_STREAM),
            hasherHandler::generate)
        .build();
  }
}
