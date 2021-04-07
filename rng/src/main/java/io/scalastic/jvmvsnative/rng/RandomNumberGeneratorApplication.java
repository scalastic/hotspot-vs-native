package io.scalastic.jvmvsnative.rng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RandomNumberGeneratorApplication {

	public static void main(String[] args) {

		SpringApplication.run(RandomNumberGeneratorApplication.class, args);

		RNGWebClient gwc = new RNGWebClient();
		System.out.println(gwc.getResult());
	}

}
