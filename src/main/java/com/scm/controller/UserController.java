package com.scm.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.config.SecurityConfig;
import com.scm.entity.Users;
import com.scm.forms.NameForm;
import com.scm.forms.PasswordForm;
import com.scm.forms.UserForm;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.service.ContactService;
import com.scm.service.ImageService;
import com.scm.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // user profile page
    @GetMapping("/profile")
    public String viewUserProfile(Map<String, Object> map, Authentication authentication) {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();
        map.put("user", user);
        return "user/profile";
    }

    @GetMapping("/editProfile")
    public String showEditProfilePage(@ModelAttribute("userForm") UserForm userForm, Authentication authentication) {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();
        userForm.setName(user.getName());
        userForm.setEmail(user.getEmail());
        userForm.setPhoneNumber(user.getPhoneNumber());
        userForm.setAbout(user.getAbout());
        userForm.setPassword(user.getPassword());
        userForm.setPic(user.getProfilePic());
        userForm.setCloudinaryImagePublicId(user.getCloudinaryImagePublicId());
        return "user/edit-profile";
    }

    @PostMapping("/editProfile")
    public String editProfile(@Valid @ModelAttribute UserForm userForm, BindingResult result,
            Authentication authentication)
            throws IOException {
        // Validate the userForm
        if (result.hasErrors()) {
            System.out.println(result.toString());
            return "user/edit-profile";
        }

        // Getting User id
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());

        // Upload picture to the cloud and get the url
        if (userForm.getProfilePic().getInputStream().available() != 0) {
            // Upload picture to the cloud and get the url
            String fileName = UUID.randomUUID().toString();
            String picUrl = imageService.uploadImage(userForm.getProfilePic(), fileName);
            user.setProfilePic(picUrl);
            user.setCloudinaryImagePublicId(fileName);
        }

        // Save user to database
        userService.updateUser(user);

        return "redirect:/user/profile";
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
    public String settings(@ModelAttribute("nameForm") NameForm nameForm,
            @ModelAttribute("passwordForm") PasswordForm passwordForm) {
        return "user/settings";
    }

    @PostMapping("/changeName")
    public String changeName(@Valid @ModelAttribute NameForm nameForm, BindingResult result,
            Authentication authentication, HttpSession session) {
        // Validate the userForm
        if (result.hasErrors()) {
            // Create Message class object
            Message message = Message.builder()
                    .content("Name should be minimum 3 characters!")
                    .type(MessageType.red)
                    .build();
            // Set message in RedirectAttributes
            session.setAttribute("message", message);
            return "redirect:/user/settings";
        }
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();
        user.setName(nameForm.getName());
        userService.updateUser(user);
        return "redirect:/user/profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@Valid @ModelAttribute PasswordForm passwordForm, BindingResult result,
            Authentication authentication, HttpSession session) {
        // Validate the userForm
        if (result.hasErrors()) {
            // Create Message class object
            Message message = Message.builder()
                    .content("Password should be minimum 8 characters!")
                    .type(MessageType.red)
                    .build();
            // Set message in RedirectAttributes
            session.setAttribute("message", message);
            return "redirect:/user/settings";
        }
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();

        // Check if current password is correct
        String existingPassword = user.getPassword();
        String currentPassword = passwordForm.getCurrentPassword();
        String newPassword = passwordForm.getNewPassword();

        if (!passwordEncoder.matches(currentPassword, existingPassword)) {
            // Create Message class object
            Message message = Message.builder()
                    .content("Incorrect current password!")
                    .type(MessageType.red)
                    .build();
            // Set message in RedirectAttributes
            session.setAttribute("message", message);
            return "redirect:/user/settings";
        }

        // Update password
        user.setPassword(newPassword);
        userService.updatePassword(user);

        return "redirect:/user/profile";
    }

    @GetMapping("deleteProfile")
    public String deleteUser(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();
        userService.deleteUserById(user.getUid());
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/logout";
    }

}
