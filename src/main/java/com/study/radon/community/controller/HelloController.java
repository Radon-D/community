package com.study.radon.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        // spring boot会根据字符串寻找到同名html文件渲染成网页
        return "helloworld";
    }

}
