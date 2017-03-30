package com.bao.service;

import com.bao.mapper.TOrderItemMapper;
import com.bao.mapper.TOrderMapper;
import com.bao.model.TOrder;
import com.bao.model.TOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by baochunyu on 2017/3/30.
 */
@Service
public class TestService {
    @Autowired
    TOrderMapper orderMapper;
    @Autowired
    TOrderItemMapper itemMapper;

    @Transactional
    public void test(){
        for (int i = 1; i < 6; i++){
            TOrder order = TOrder.builder().userId(i).orderId(i).orderName("order"+i).build();
            TOrderItem item = TOrderItem.builder().itemId(i).userId(i).orderId(i).itemName("item"+i).build();
            orderMapper.insert(order);
            itemMapper.insert(item);
        }
        throw new RuntimeException();
    }
}
