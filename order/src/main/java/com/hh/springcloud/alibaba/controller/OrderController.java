package com.hh.springcloud.alibaba.controller;

import com.hh.springcloud.alibaba.service.PayService;
import com.hh.springcloud.entity.CommonResult;
import com.hh.springcloud.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 消费者，调用支付
 *
 * @author HH
 * @version 1.0
 * @time 2024/02/01  Thu  14:25
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    //使用服务名进行负载均衡
    public static final String SERVICE_URL = "http://pay-service";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbon/{id}")
    public CommonResult<Payment> ribbon(@PathVariable("id") Long id) {
        CommonResult<Payment> res = restTemplate.getForObject(SERVICE_URL + "/pay/" + id, CommonResult.class, id);
        return res;
    }

    @Autowired
    private PayService payService;

    @GetMapping("/feign/{id}")
    public CommonResult<Payment> feign(@PathVariable("id") Long id) {
        CommonResult<Payment> res = payService.pay(id);
        return res;
    }

}
