package com.hh.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author HH
 * @version 1.0
 * @time 2024/02/03  Sat  16:11
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() throws InterruptedException {
        //用于线程阈值测试
//        TimeUnit.MILLISECONDS.sleep(5000);
        log.info(Thread.currentThread().getName() + "\t" + "****testA****");
        return "****testA****";
    }

    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t" + "****testB****");
        return "****testB****";
    }

    @GetMapping("/testD")
    public String testD() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(1);
        int i = 1 / 0;
        log.info(Thread.currentThread().getName() + "\t" + "****testD****");
        return "****testD****";
    }

    /**
     * 热点参数限流
     * 必须使用@SentinelResource注解，不支持private方法
     *
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "dealTestHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1, @RequestParam(value = "p2", required = false) String p2) {
        log.info(Thread.currentThread().getName() + "\t" + "****testHotKey****");
        return "****testHotKey****";
    }

    /**
     * 降级方法
     * 这里方法返回，参数签名需要和原方法一致，且在参数最后增加BlockException
     * 如果没有该方法，那么会抛出com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException: 1
     *
     * @param p1
     * @param p2
     * @param ex
     * @return
     */
    public String dealTestHotKey(String p1, String p2, BlockException ex) {
        return "****dealTestHotKey(...)****";
    }
}
