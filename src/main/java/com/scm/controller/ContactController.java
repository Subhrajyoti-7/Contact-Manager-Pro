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
import org.springframework.web.bind.annotation.PathVariable;
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

        return "redirect:/user/contacts/viewContacts";
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

    @GetMapping("/editContact/{contactId}")
    public String showEditCotactPage(@PathVariable String contactId,
            @ModelAttribute("contactForm") ContactForm contactForm, Map<String, Object> map) {
        // Fetch contact details
        Contacts contact = service.fetchContactById(contactId).orElse(null);
        if (contact != null) {
            // Populate the contactForm with contact details
            contactForm.setName(contact.getName());
            contactForm.setEmail(contact.getEmail());
            contactForm.setPhone(contact.getPhone());
            contactForm.setAddress(contact.getAddress());
            contactForm.setPicture(contact.getPic());
            contactForm.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
            contactForm.setFacebook(contact.getFacebook());
            contactForm.setInstagram(contact.getInstagram());

            map.put("contactId", contactId);
        }
        return "user/edit-contact";
    }

    @PostMapping("/editContact/{contactId}")
    public String editCotact(@PathVariable String contactId,
            @Valid @ModelAttribute("contactForm") ContactForm contactForm, BindingResult result,
            Authentication authentication)
            throws IOException {
        // Validate the contactForm
        if (result.hasErrors()) {
            return "user/edit-contact";
        }

        Contacts contact = service.fetchContactById(contactId).get();
        contact.setCid(contactId);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setAddress(contactForm.getAddress());
        contact.setFacebook(contactForm.getFacebook());
        contact.setInstagram(contactForm.getInstagram());
        // Update contact details
        if (contactForm.getPic().getInputStream().available() != 0) {
            // Upload picture to the cloud and get the url
            String fileName = UUID.randomUUID().toString();
            String picUrl = imageService.uploadImage(contactForm.getPic(), fileName);
            contact.setPic(picUrl);
            contact.setCloudinaryImagePublicId(fileName);
        }

        // Update contact in the database
        service.updateContact(contact);

        return "redirect:/user/contacts/viewContacts";
    }

    // If not using api calling by fetch api
    // @GetMapping("/deleteContact/{contactId}")
    // public String deleteContact(@RequestParam String contactId) {
    // // Delete contact from the database
    // System.out.println("Contact id :: " + contactId);
    // service.deleteContactById(contactId);
    // return "redirect:/viewContacts";
    // }

}
