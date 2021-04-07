package io.scalastic.jvmvsnative.hasher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class HasherServiceTests {

    @Autowired
    HasherService hasherService;

    @Test
    void hash() {

        String result = hasherService.hash("123456789");

        Assert.notNull(result);
        Assert.isTrue(result.contentEquals("f7c3bc1d808e04732adf679965ccc34ca7ae3441"));
        System.out.println("Test HASH \"123456789\" = " + result);

    }

}
