package com.shopFinal.shopFinal.controllers;

import com.shopFinal.shopFinal.model.RoleEnum;
import com.shopFinal.shopFinal.model.UserModel;
import com.shopFinal.shopFinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registrationView(){
        return "regis";
    }

    @PostMapping("/registration")
    public String registrationUser(UserModel user, Model model){
        if(userRepository.existsByUsername(user.getUsername())){
            model.addAttribute("message", "Пользователь уже существует");
            return "regis";

        }
        if(!user.getPassword().matches(".*[!@#$%^&*(),.?\":{}|<>].*") || user.getPassword().length() <= 7){
            model.addAttribute("password_message","Пароль должен содержать хотя бы 1 спец символ и иметь длину не менее 7 символов");
            return "regis";
        }
        user.setDeleted(false);
        user.setRoles(Collections.singleton(RoleEnum.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("userRole", getRole());
        return "redirect:/login";
    }

    private String getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = "";

        if (authentication != null && authentication.isAuthenticated()) {

            userRole = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(null);
        }

        return userRole;
    }
}
