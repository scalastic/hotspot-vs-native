package io.scalastic.jvmvsnative.hasher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class HasherHandler {

    @Autowired
    private HasherService hasherService;

    public Mono<ServerResponse> generate(ServerRequest request) {

        return request.bodyToMono(String.class)
                .flatMap(data -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .bodyValue(hasherService.hash(data))
        );
    }


}
