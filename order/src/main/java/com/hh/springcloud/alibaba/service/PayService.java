package com.hh.springcloud.alibaba.service;

import com.hh.springcloud.entity.CommonResult;
import com.hh.springcloud.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author HH
 * @version 1.0
 * @time 2024/02/01  Thu  14:41
 */
@FeignClient(value = "pay-service")
public interface PayService {
    @GetMapping("/pay/{id}")
    CommonResult<Payment> pay(@PathVariable("id") Long id);
}
