package com.bao.liner;

import com.bao.mapper.TOrderItemMapper;
import com.bao.mapper.TOrderMapper;
import com.bao.model.TOrder;
import com.bao.model.TOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by baochunyu on 2017/3/15.
 */
@Component
public class TestLiner implements CommandLineRunner {

    @Autowired
    TOrderMapper orderMapper;
    @Autowired
    TOrderItemMapper itemMapper;

    @Override
    public void run(String... strings) throws Exception {


//        for (int i = 1; i < 6; i++){
//            TOrder order = TOrder.builder().userId(i).orderId(i).orderName("order"+i).build();
//            TOrderItem item = TOrderItem.builder().itemId(i).userId(i).orderId(i).itemName("item"+i).build();
//            orderMapper.insert(order);
//            itemMapper.insert(item);
//        }

    }
}
