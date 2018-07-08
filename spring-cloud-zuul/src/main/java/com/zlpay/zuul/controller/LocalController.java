package com.zlpay.zuul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalController {

	@RequestMapping("/local")
    public String hello() {
        return "这是 一个zuul本地请求";
    }
}
