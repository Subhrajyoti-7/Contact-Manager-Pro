package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entity.Users;
import com.scm.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify-email")
    public String verifyUser(@RequestParam("token") String token) {
        // Logic to verify User
        Users user = userService.getUserByToken(token).get();
        if (user.getEmailToken().equals(token)) {
            // Update user status to verified
            user.setEmailVerified(true);
            user.setEnabled(true);
            userService.updateUser(user);
            return "success-page";
        }

        // Redirect to Gmail
        // return new RedirectView("https://mail.google.com/");

        // OR
        return "error-page";
    }

}
