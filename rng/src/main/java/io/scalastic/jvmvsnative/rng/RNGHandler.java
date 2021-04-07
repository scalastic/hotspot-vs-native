package io.scalastic.jvmvsnative.rng;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Component
public class RNGHandler {

    public Mono<ServerResponse> rng(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(RNGHandler.generateRandom(32)));
    }

   public static String generateRandom(int digits) {

        if (digits <= 0) {
            throw new IllegalArgumentException("digits must be positive");
        }

        String chars = "0123456789";
        String str = new SecureRandom()
                .ints(digits, 0, chars.length())
                .mapToObj(i -> "" + chars.charAt(i))
                .collect(Collectors.joining());

        return str;
    }
}
