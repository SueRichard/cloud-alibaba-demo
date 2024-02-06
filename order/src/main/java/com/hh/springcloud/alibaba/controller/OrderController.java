package com.hh.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hh.springcloud.alibaba.service.PayService;
import com.hh.springcloud.entity.CommonResult;
import com.hh.springcloud.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

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

    /**
     * exceptionsToIgnore 这里设置的异常会忽略，会按照系统抛异常的方法处理。页面就是错误页面之类
     *
     * @param id
     * @return
     */
    @GetMapping("/ribbon/{id}")
    @SentinelResource(value = "ribbon", fallback = "fallback", blockHandler = "blockHandler", exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> ribbon(@PathVariable("id") Long id) {
        CommonResult<Payment> res = restTemplate.getForObject(SERVICE_URL + "/pay/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("id=4，非法参数异常");
        } else if (res.getData() == null) {
            throw new NullPointerException("空指针异常，id不存在");
        }
        return res;
    }

    public CommonResult<Payment> fallback(Long id, Throwable tr) {
        Payment payment = new Payment(id, "业务异常");
        return new CommonResult<>(444, "业务兜底方法，tr =" + tr.getMessage(), payment);
    }

    public CommonResult<Payment> blockHandler(Long id, BlockException ex) {
        Payment payment = new Payment(id, "控制台违规异常");
        return new CommonResult<>(445, "控制台违规，ex =" + ex.getMessage(), payment);

    }

    @Autowired
    private PayService payService;

    @GetMapping("/feign/{id}")
    public CommonResult<Payment> feign(@PathVariable("id") Long id) {
        CommonResult<Payment> res = payService.pay(id);
        return res;
    }

}
