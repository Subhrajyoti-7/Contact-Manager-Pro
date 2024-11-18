package com.scm.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entity.Feedback;
import com.scm.entity.Users;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.service.FeedbackService;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private Environment env;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Map<String, Object> attr) {
        attr.put("message", "Welcome to Smart Contact Manager!");
        return "home";
    }

    @GetMapping("/service")
    public String services() {
        return "service";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact-us")
    public String contacts() {
        return "contact-us";
    }

    @GetMapping("/register")
    public String showRegisterPage(@ModelAttribute("userForm") UserForm userForm) {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute UserForm userForm, BindingResult result, HttpSession session) {
        // Validate the userForm
        if (result.hasErrors()) {
            return "register";
        }

        // Checks whether the user with same email already exists
        if (userService.isUserExistByEmail(userForm.getEmail())) {
            result.rejectValue("email", "error.user", "Email already exists");
            return "register";
        }

        /*
         * Users user = new Users();
         * user.setName(userForm.getName());
         * user.setEmail(userForm.getEmail());
         * user.setPhoneNumber(userForm.getPhoneNumber());
         * user.setAbout(userForm.getAbout());
         * user.setPassword(userForm.getPassword());
         */
        // OR
        // Create user object from userForm using builder method
        Users user = Users.builder()
                .name(userForm.getName())
                .email(userForm.getEmail())
                .phoneNumber(userForm.getPhoneNumber())
                .about(userForm.getAbout())
                .password(userForm.getPassword())
                .profilePic(env.getProperty("profile-picture-default"))
                .build();
        // Save user to database
        userService.saveUser(user);

        // Create Message class object
        Message message = Message.builder()
                .content("Registration successful!")
                .type(MessageType.green)
                .build();

        // Set message in RedirectAttributes
        session.setAttribute("message", message);

        // Redirect to login page
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/feedback")
    public String saveFeedback(@ModelAttribute Feedback feed, HttpSession session) {
        // Creating feedback id
        String feedbackId = UUID.randomUUID().toString();

        // Setting data from Model attribute to Feedback
        Feedback feedback = new Feedback();
        feedback.setFid(feedbackId);
        feedback.setName(feed.getName());
        feedback.setEmail(feed.getEmail());
        feedback.setMsg(feed.getMsg());

        // Save feedback to database
        feedbackService.saveFeedback(feedback);

        // Create Message class object
        Message message = Message.builder()
                .content("Thank you for giving your feedback!")
                .type(MessageType.green)
                .build();
        // Set message in HttpSession
        session.setAttribute("message", message);

        // Redirect to asme page
        return "redirect:/contact-us";
    }

}
