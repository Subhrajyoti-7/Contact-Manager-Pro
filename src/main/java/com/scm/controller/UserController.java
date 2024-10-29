package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    // user dashboard page
    @GetMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    // user profile page
    @GetMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }

    // add contact page
    @GetMapping("/add-contact")
    public String addContact() {
        return "user/add_contact";
    }

    // edit contact page
    @GetMapping("/edit-contact")
    public String editContact() {
        return "user/edit_contact";
    }

    // delete contact page
    @GetMapping("/delete-contact")
    public String deleteContact() {
        return "user/delete_contact";
    }

    // view contact page
    @GetMapping("/view-contact")
    public String showContactPage() {
        return "user/contact";
    }

}
