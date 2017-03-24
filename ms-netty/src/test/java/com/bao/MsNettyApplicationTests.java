package com.bao;

import com.bao.retry.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MsNettyApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Autowired
	TestService testService;
	@Test
	public void retryTest(){
		testService.service1();
	}

}
