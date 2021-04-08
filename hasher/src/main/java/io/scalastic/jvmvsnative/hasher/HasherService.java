package io.scalastic.jvmvsnative.hasher;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HasherService {
  
  public String hash(String digits) {
    
    if (digits.isEmpty()) {
      throw new IllegalArgumentException("digits must not be empty");
    }
    
    final MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage());
    }
    final byte[] hashbytes = digest.digest(digits.getBytes(StandardCharsets.UTF_8));
    String sha1Hex = bytesToHex(hashbytes);
    
    return sha1Hex;
  }
  
  private String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
