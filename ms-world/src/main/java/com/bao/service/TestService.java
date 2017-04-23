package com.bao.service;

import com.bao.mapper.TOrderItemMapper;
import com.bao.mapper.TOrderMapper;
import com.bao.model.TOrder;
import com.bao.model.TOrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by baochunyu on 2017/4/23.
 */
@Slf4j
@Service
public class TestService {

    @Autowired
    TOrderMapper tOrderMapper;
    @Autowired
    TOrderItemMapper tOrderItemMapper;

    public void without() {
        log.info("1");
        test1();
        log.info("2");
    }

    @Transactional
    public void test() {
        log.info("1");
        test1();
        log.info("2");
    }

    @Transactional
    public void test1() {
        TOrder order = TOrder.builder().userId(11).orderId(11).orderName("order"+11).build();
        TOrderItem item = TOrderItem.builder().itemId(11).userId(11).orderId(11).itemName("item"+11).build();
        tOrderMapper.insert(order);
        tOrderItemMapper.insert(item);
        throw new RuntimeException();
    }

    @Autowired
    OrderService orderService;

    public void test2(){
        orderService.test1();
    }
}
