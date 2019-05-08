package com.fycstart.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/5下午 2:18
 */
@Controller
public class HomeController {

    @GetMapping("/logout/page")
    public String loginOut() {
        return "logout";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
