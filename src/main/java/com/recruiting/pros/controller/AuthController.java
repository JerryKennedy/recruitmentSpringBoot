package com.recruiting.pros.controller;

import com.recruiting.pros.dto.UserDto;
import com.recruiting.pros.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;

    }

    // Mapping for home page
    @GetMapping("/")
    public String home(){
        return "index";
    }

//     Mapping for registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // Mapping for login page
    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }


//     Mapping for handling user registration
    @PostMapping("/register")
    public String registration(@ModelAttribute("user") UserDto userDto){
        userService.saveUser(userDto);
        return "redirect:/login";
    }

}
