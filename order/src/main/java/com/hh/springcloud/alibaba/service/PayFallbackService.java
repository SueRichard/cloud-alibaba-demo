package com.hh.springcloud.alibaba.service;

import com.hh.springcloud.entity.CommonResult;
import com.hh.springcloud.entity.Payment;
import org.springframework.stereotype.Component;

/**
 * open feign降级处理类
 *
 * @author HH
 * @version 1.0
 * @time 2024/02/06  Tue  15:59
 */
@Component
public class PayFallbackService implements PayService {
    @Override
    public CommonResult<Payment> pay(Long id) {
        return new CommonResult<>(446, "open feign 降级处理函数", new Payment(id, "feign 调用异常"));
    }
}
