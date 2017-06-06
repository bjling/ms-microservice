package com.bao.msdatasources.service;

import com.bao.msdatasources.config.DataBaseSelector;
import com.bao.msdatasources.mapper.TOrderItemMapper;
import com.bao.msdatasources.mapper.TOrderMapper;
import com.bao.msdatasources.model.TOrder;
import com.bao.msdatasources.model.TOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nannan on 2017/6/6.
 */
@Service
@DataBaseSelector(name = "test1")
public class TestService {

    @Autowired
    TOrderMapper orderMapper;

    @Autowired
    TOrderItemMapper itemMapper;

    public void test() {
        TOrder tOrder = TOrder.builder().orderId(11).userId(11).orderName("test11").build();
        orderMapper.insertSelective(tOrder);

        TOrderItem item = TOrderItem.builder().itemId(11).orderId(11).userId(11).itemName("test11").build();
        itemMapper.insertSelective(item);
    }
}
