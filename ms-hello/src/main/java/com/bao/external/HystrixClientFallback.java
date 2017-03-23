package com.bao.external;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by baochunyu on 2017/3/22.
 */
@Component
public class HystrixClientFallback  implements WorldClient {
    @Override
    public String world() {
        return "fallback";
    }
}
