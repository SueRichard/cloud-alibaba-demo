package com.hh.springcloud.alibaba.controller;

import com.hh.springcloud.entity.CommonResult;
import com.hh.springcloud.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author HLLG
 * @version 1.0
 * @time 2024/02/01  Thu  13:51
 */
@RestController
public class PayController {

    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> map = new HashMap<>();

    static {
        map.put(1l, new Payment(1l, "1"));
        map.put(2l, new Payment(2l, "2"));
        map.put(3l, new Payment(3l, "3"));
    }

    @GetMapping("/pay/{id}")
    public CommonResult<Payment> pay(@PathVariable("id") Long id) {
        Payment payment = map.get(id);
        CommonResult<Payment> res = new CommonResult<>(200, "server port:" + serverPort, payment);
        return res;

    }


}
