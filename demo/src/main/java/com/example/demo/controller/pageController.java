package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class pageController {
    @GetMapping("/")
    public String home(Model model) throws Exception {
        //model.addAttribute("name", "ControllerTest");
        System.out.println("hello");
        return "hello";
    }
    @GetMapping(value = "/home")
    public String test () throws Exception {
        //model.addAttribute("name", "ControllerTest");
        System.out.println("test");
        return "test";
    }
}

