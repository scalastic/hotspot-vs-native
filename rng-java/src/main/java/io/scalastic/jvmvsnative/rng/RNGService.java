package io.scalastic.jvmvsnative.rng;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Service
public class RNGService {
  
  public String generateRandom(int digits) {
    
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
