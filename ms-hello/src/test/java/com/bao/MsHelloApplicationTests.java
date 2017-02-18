package com.bao;

import com.bao.annotation.Eat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MsHelloApplicationTests {

	@Autowired
	Eat eat;

	@Test
	public void contextLoads() {
		String a = eat.eating();
		System.out.println(a);

	}

}
