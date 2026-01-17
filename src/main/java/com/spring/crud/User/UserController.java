package com.spring.crud.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/User")
public class UserController {

    @GetMapping()
    public String hola(){
        return"hola";
    }
}
