package com.bao.service;

import com.bao.mapper.TOrderItemMapper;
import com.bao.mapper.TOrderMapper;
import com.bao.model.TOrder;
import com.bao.model.TOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by baochunyu on 2017/4/23.
 */
@Service
public class OrderService {
    @Autowired
    TOrderMapper tOrderMapper;
    @Autowired
    TOrderItemMapper tOrderItemMapper;

    @Transactional
    public void test1() {
        TOrder order = TOrder.builder().userId(11).orderId(11).orderName("order" + 11).build();
        TOrderItem item = TOrderItem.builder().itemId(11).userId(11).orderId(11).itemName("item" + 11).build();
        tOrderMapper.insert(order);
        tOrderItemMapper.insert(item);
        throw new RuntimeException();
    }
}
