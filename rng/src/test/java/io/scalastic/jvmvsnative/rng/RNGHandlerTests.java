package io.scalastic.jvmvsnative.rng;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class RNGHandlerTests {

    @Test
    void generateRandom() {

        /*for (int i = 1; i <= 50; ++i) {
            String rn = RNGHandler.generateRandom(i);

            System.out.printf("For i = %d, random = %s%n", i, rn);
            Assert.isTrue(i == rn.length(), String.format("generateRandom should return a number with %d digits !", i));
        }*/
    }
}
