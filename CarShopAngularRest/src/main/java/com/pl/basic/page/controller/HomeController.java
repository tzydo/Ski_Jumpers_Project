package com.pl.basic.page.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api", method = RequestMethod.GET)
public class HomeController {

    private Title title;

    @RequestMapping(value = "*", method = RequestMethod.GET)
    public String redirect() {
        return "redirect:/ap/home";
    }

    @ResponseBody
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public Title homePage(){
        this.title = Title.builder()
                        .title("Hello")
                        .name("Adam")
                        .lastName("Nowak")
                        .email("super@nowak").build();
        return this.title;
    }

}

