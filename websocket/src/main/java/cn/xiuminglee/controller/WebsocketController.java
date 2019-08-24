package cn.xiuminglee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/8/20 7:43
 * @Version 1.0
 * @Describe:
 */
@Controller
@RequestMapping("/index")
public class WebsocketController {
    @RequestMapping
    public String mainPage(){
        return "user";
    }
    @RequestMapping("admin")
    public String adminPage(){
        return "admin";
    }

}
