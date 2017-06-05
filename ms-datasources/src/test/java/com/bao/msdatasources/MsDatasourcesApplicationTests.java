package com.bao.msdatasources;

import com.bao.msdatasources.mapper.TOrderItemMapper;
import com.bao.msdatasources.mapper.TOrderMapper;
import com.bao.msdatasources.model.TOrder;
import com.bao.msdatasources.model.TOrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MsDatasourcesApplicationTests {

	@Autowired
	TOrderMapper orderMapper;

	@Autowired
	TOrderItemMapper itemMapper;

	@Test
	public void contextLoads() {

		TOrder tOrder = TOrder.builder().orderId(11).userId(11).orderName("test11").build();
		orderMapper.insertSelective(tOrder);

		TOrderItem item = TOrderItem.builder().itemId(11).orderId(11).userId(11).itemName("test11").build();
		itemMapper.insertSelective(item);


	}

}
