package io.scalastic.jvmvsnative.rng;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.Collectors;

@Service
public class RNGService {
  
  public String generateRandom(int digits) {
    
    if (digits <= 0) {
      throw new IllegalArgumentException("digits must be positive");
    }
  
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  
    String chars = "0123456789";
    String str = null;
    try {
      str = SecureRandom.getInstance("NativePRNGNonBlocking")
          .ints(digits, 0, chars.length())
          .mapToObj(i -> "" + chars.charAt(i))
          .collect(Collectors.joining());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  
    return str;
  }
  
}
