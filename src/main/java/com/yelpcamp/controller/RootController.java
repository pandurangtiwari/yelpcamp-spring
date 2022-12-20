package com.yelpcamp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import static com.yelpcamp.controller.ControllerConstants.*;

@Controller
public class RootController {

    @GetMapping(value = ROUTES.HOME)
    public String renderHomePage(){
        return VIEWS.HOME;
    }

    @RequestMapping("/*")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String renderNotFound(){
        return VIEWS.NOT_FOUND;
    }
}
