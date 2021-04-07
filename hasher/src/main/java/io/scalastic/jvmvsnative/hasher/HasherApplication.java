package io.scalastic.jvmvsnative.hasher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HasherApplication {

	public static void main(String[] args) {

		SpringApplication.run(HasherApplication.class, args);

		HasherWebClient hwc = new HasherWebClient();
		hwc.getResult();
	}

}
