package com.bao.external;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by baochunyu on 2017/3/27.
 */
@FeignClient("ms-world")
public interface WorldClient {
    @RequestMapping(value = "/world", method = RequestMethod.GET)
    String world();
}
