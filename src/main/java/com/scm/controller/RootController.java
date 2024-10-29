package com.scm.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entity.Users;
import com.scm.helper.Helper;
import com.scm.service.UserService;

@ControllerAdvice
public class RootController { // Executes for every request

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void getLoggedInUserInfo(Map<String, Object> map, Authentication authentication) {
        if (authentication != null) {
            String email = Helper.getEmailOfLoggedInUser(authentication);
            Users user = new Users();
            Optional<Users> opUser = userService.findByEmail(email);
            if (opUser.isPresent()) {
                user = opUser.get();
            }
            map.put("user", user);
        } else {
            map.put("user", null);
        }
    }

}
