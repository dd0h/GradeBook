package com.gradebook;


import com.gradebook.entities.users.User;
import com.gradebook.entities.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(path = "/register")
    public String addNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "fragments/forms/register";
    }

    @PostMapping(path = "/register") // Map ONLY POST Requests
    public String addNewUserSubmit(@Valid @ModelAttribute User user,
                                   BindingResult result,
                                   RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("register_fail", "Check if you have all fields!");
            return "fragments/forms/register";
        } else {
            if (userRepository.existsByUsername(user.getUsername())) {
                attributes.addFlashAttribute("register_fail", "User with that name already exists!");
                return "redirect:/register";
            } else if (userRepository.existsByEmail(user.getEmail())) {
                attributes.addFlashAttribute("register_fail", "This email is already in use!");
                return "redirect:/register";
            } else if(!user.getPassword().equals(user.getPassword_repeated())) {
                attributes.addFlashAttribute("register_fail", "Passwords do not match!");
                return "redirect:/register";
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                attributes.addFlashAttribute("register_success", "Your registration was successful!");
                return "redirect:/login";
            }
        }
    }

    @GetMapping(path="/info")
    public String Info(@AuthenticationPrincipal UserDetailsImpl principal, Model model){
        model.addAttribute("userinfo", userRepository.findByUsername(principal.getUsername()).get());
        return "fragments/userprofile";
    }
}



