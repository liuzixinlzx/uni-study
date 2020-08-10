package com.lzx.uniserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages={"com.lzx.*"})
class UniserverApplicationTests {

    @Test
    void contextLoads() {
    }

}
