package com.bao;

import com.bao.annotation.Eat;
import com.bao.executor.ExecutorPool;
import com.bao.task.ListTask;
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

	@Autowired
	ExecutorPool executorPool;

	@Test
	public void contextLoads() {
		String a = eat.eating();
		System.out.println(a);

		ListTask task = new ListTask();

	}

}
