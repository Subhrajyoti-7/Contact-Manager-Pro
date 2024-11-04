package com.scm.controller;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entity.Contacts;
import com.scm.entity.Users;
import com.scm.forms.ContactForm;
import com.scm.helper.Helper;
import com.scm.service.ContactService;
import com.scm.service.ImageService;
import com.scm.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService service;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/addContact")
    public String showCotactAddPage(@ModelAttribute("contactForm") ContactForm contactForm) {
        return "user/add-contact";
    }

    @PostMapping("/addContact")
    public String addCotacts(@Valid @ModelAttribute("contactForm") ContactForm contactForm, BindingResult result,
            Authentication authentication)
            throws IOException {
        // Validate the contactForm
        if (result.hasErrors()) {
            return "user/add-contact";
        }

        // Getting User information
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();

        // Upload picture to the cloud and get the url
        String fileName = UUID.randomUUID().toString();
        String picUrl = "";
        if (contactForm.getPic().getInputStream().available() == 0)
            picUrl = "user-default.png";
        else
            picUrl = imageService.uploadImage(contactForm.getPic(), fileName);

        // Retriving data from the contactForm and adding it to the Contacts
        Contacts contact = Contacts.builder()
                .name(contactForm.getName())
                .email(contactForm.getEmail())
                .phone(contactForm.getPhone())
                .address(contactForm.getAddress())
                .pic(picUrl)
                .cloudinaryImagePublicId(fileName)
                .facebook(contactForm.getFacebook())
                .instagram(contactForm.getInstagram())
                .user(user)
                .build();

        // Save contact to database
        service.saveContact(contact);

        return "user/dashboard";
    }

    @GetMapping("viewContacts")
    public String showAllContacts(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "sortField", defaultValue = "name") String sortField,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Authentication authentication,
            Map<String, Object> map) {
        // Getting User information
        String email = Helper.getEmailOfLoggedInUser(authentication);
        Users user = userService.findByEmail(email).get();
        // Fetch all contacts for the user
        Page<Contacts> contacts = service.fetchAllContactsByUser(user, page, pageSize, sortField, direction);
        map.put("contacts", contacts);
        map.put("user", user);

        return "user/contacts";
    }

}
