package com.bao.msdatasources;

import com.bao.msdatasources.mapper.TOrderItemMapper;
import com.bao.msdatasources.mapper.TOrderMapper;
import com.bao.msdatasources.model.TOrder;
import com.bao.msdatasources.model.TOrderItem;
import com.bao.msdatasources.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MsDatasourcesApplicationTests {

    @Autowired
    TestService testService;

    @Test
    public void contextLoads() {
        testService.test();
    }

}
