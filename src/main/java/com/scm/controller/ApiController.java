package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.Contacts;
import com.scm.service.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${server.baseUrl}")
    private String baseUrl;

    @Autowired
    private ContactService contactService;

    // Get contact information
    @GetMapping("/showContact/{contactId}")
    public Contacts showContact(@PathVariable String contactId) {
        return contactService.fetchContactById(contactId).get();
    }

    // Use if you want to use modals and api calling using fetch api
    @GetMapping("/deleteContact/{contactId}")
    public String deleteContact(@PathVariable String contactId) {
        contactService.deleteContactById(contactId);
        return "Contact with id " + contactId + " is deleted";
    }

    @GetMapping("/baseUrl")
    public String getBaseUrl() {
        // return string value as json
        return "{\"baseUrl\": \"" + baseUrl + "\"}";
    }
}
