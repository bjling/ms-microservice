package com.bao;

import com.bao.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MsWorldApplicationTests {

    @Autowired
    TestService testService;

    @Test
    public void contextLoads() {
        //not transaction
        //testService.without();
        //yes transaction
        //testService.test();
        //yes transaction
        testService.test2();
    }

}
