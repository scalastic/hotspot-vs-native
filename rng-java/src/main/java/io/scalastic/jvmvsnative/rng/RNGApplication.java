package io.scalastic.jvmvsnative.rng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RNGApplication {
  
  public static void main(String[] args) {
    
    SpringApplication.run(RNGApplication.class, args);
    
    RNGWebClient gwc = new RNGWebClient();
    gwc.getResult();
  }
  
}
