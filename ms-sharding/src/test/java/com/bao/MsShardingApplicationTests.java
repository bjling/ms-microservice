package com.bao;

import com.bao.mapper.UserMapper;
import com.bao.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class MsShardingApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void insertTetst(){
		User user = User.builder().name("bao").userId(1).age(30).build();
		int i = userMapper.insertSelective(user);
		assertEquals(1,i);
	}


}
