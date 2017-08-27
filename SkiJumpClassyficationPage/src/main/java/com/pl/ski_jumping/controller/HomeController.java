package com.pl.ski_jumping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String homePage(){
        return "home";
    }
}
