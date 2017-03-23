package com.bao.external;

import com.bao.feign.FeignConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by baochunyu on 2017/3/22.
 */

@FeignClient(name = "ms-world")
public interface WorldClient {

    @RequestMapping(value = "/world", method = RequestMethod.GET)
    String world();

}
