package com.example.springbootcrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String IndexView() { return "index.html"; }



    @GetMapping("/sign_up")
    public String SignUpView() {
        return "sign_up.html";
    }

}
