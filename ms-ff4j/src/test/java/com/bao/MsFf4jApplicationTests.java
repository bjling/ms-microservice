package com.bao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MsFf4jApplicationTests {

	@Test
	public void contextLoads() {
		int NCPU = Runtime.getRuntime().availableProcessors();

		System.out.println(NCPU);
		System.out.println(Runtime.getRuntime().totalMemory()/(1024*1024));
	}

}
