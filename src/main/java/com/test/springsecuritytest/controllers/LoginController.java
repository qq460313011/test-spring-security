package com.test.springsecuritytest.controllers;


import com.test.springsecuritytest.model.UserEntity;
import com.test.springsecuritytest.services.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login() {
        return "/login.html";
    }

    @RequestMapping("/index")
    public String index() {
        return "/index.html";
    }

    @RequestMapping("/page1")
    public String page1(Model model) {
        model.addAttribute("username", userService.getCurrentUserName());
        return "/page/page1.html";
    }

    @RequestMapping("/page2")
    public String page2(Model model) {
        model.addAttribute("username", userService.getCurrentUserName());
        return "/page/page2.html";
    }

    @RequestMapping("/page3")
    //@Secured("p3:/page3")
    @PreAuthorize("hasAuthority('p3')")
   // @PostAuthorize("hasAuthority('p3')")
    public String page3(Model model) {

        model.addAttribute("username", userService.getCurrentUserName());
        return "/page/page3.html";
    }
}
