package com.scm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entity.Users;
import com.scm.helper.Helper;
import com.scm.service.ContactService;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    // user profile page
    @GetMapping("/profile")
    public String viewUserProfile(Map<String, Object> map, Authentication authentication) {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();
        map.put("user", user);
        return "user/profile";
    }

    // user dashboard page
    @GetMapping("/dashboard")
    public String viewUserDashboard(Authentication authentication, HttpSession session) {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();
        int count = contactService.countContacts(user.getUid());
        session.setAttribute("contactCount", count);
        return "user/dashboard";
    }

    @GetMapping("/settings")
    public String settings() {
        return "user/settings";
    }

}
