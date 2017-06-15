package com.bao.external;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by baochunyu on 2017/3/22.
 */

@FeignClient(name = "ms-netty",url = "http://baidu.com")
public interface NettyClient {

    @RequestMapping(value = "/netty", method = RequestMethod.GET)
    String netty();

}
