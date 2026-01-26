package edu.bridgerdavidson.products.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestControlller {
    @GetMapping("/whoami")
    @ResponseBody
    public Object whoami(Authentication auth) {
        return auth.getAuthorities();
    }

}
