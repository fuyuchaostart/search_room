package com.fycstart.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 瓦力.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @RequestMapping("/center")
    public String centerPage() {
        return "user/center";
    }

    @GetMapping("/logout/page")
    public String logoutPage() {
        return "logout";
    }

}
