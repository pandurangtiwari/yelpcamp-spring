package com.yelpcamp.controller;

import com.yelpcamp.exception.UserAlreadyExistException;
import com.yelpcamp.model.User;
import com.yelpcamp.repository.UserRepository;
import com.yelpcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.yelpcamp.controller.ControllerConstants.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(ROUTES.REGISTER)
    public String renderRegistrationForm(Model model){
        model.addAttribute(ATTRIBUTES.USER, new User());
        return VIEWS.REGISTER;
    }

    @PostMapping(ROUTES.REGISTER)
    public String registerUser(@ModelAttribute @Valid User user,
                               RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(user);
        } catch (UserAlreadyExistException ex) {
            redirectAttributes.addFlashAttribute(ATTRIBUTES.ERROR, ex.getMessage());
            return ROUTES.REGISTER_REDIRECT;
        }
        redirectAttributes.addFlashAttribute(ATTRIBUTES.SUCCESS, "User registered Successfully");
        return ROUTES.LOGIN_REDIRECT;
    }

    @GetMapping(ROUTES.LOGIN)
    public String renderLoginForm(){
        return VIEWS.LOGIN;
    }
}
