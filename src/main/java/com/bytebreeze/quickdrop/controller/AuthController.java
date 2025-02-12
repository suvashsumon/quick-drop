package com.bytebreeze.quickdrop.controller;


import com.bytebreeze.quickdrop.dto.UserRegistrationRequestDTO;
import com.bytebreeze.quickdrop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserService userService;

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userRegistrationRequestDTO",new UserRegistrationRequestDTO());

        return "auth/register";
    }

    @PostMapping ("/register")
    public String submitRegistrationForm(@Valid @ModelAttribute("userRegistrationRequestDTO") UserRegistrationRequestDTO userRegistrationRequestDTO,
                                         BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("error");
            //bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            // Collect all validation error messages
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()) // Get default messages
                    .collect(Collectors.toList());

            // Add error messages to the model
            model.addAttribute("validationErrors", errorMessages);

            return "auth/register";
        }

        try{
            userService.registerUser(userRegistrationRequestDTO);

            redirectAttributes.addFlashAttribute("success", true); // Add flash attribute
            return "redirect:/auth/login";

           //return "redirect:/auth/login?success=true";
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "auth/register";
        }
    }







    //-------------------------------------------------------------------------------------------------



    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("message", "Login to your account");
        return "auth/login";  // Removed leading slash
    }


    @GetMapping("/forget-password")
    public String forgetPassword(Model model) {
        model.addAttribute("message", "Recover your account");
        return "auth/forget-password";  // Removed leading slash
    }

    @GetMapping("/reset-password")
    public String resetPassword(Model model) {
        model.addAttribute("message", "Reset your password");
        return "auth/reset-password";  // Removed leading slash
    }



}
