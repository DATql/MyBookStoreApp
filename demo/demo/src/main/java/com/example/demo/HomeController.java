package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String first(){
        return "ok2";
    }
    @GetMapping("/home")
    public String home() {
        return "ok";
    }

    @GetMapping("/admin")
    public String admin() {
        return "okeeee";
    }

}
