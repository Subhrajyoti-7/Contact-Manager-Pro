package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entity.Contacts;
import com.scm.service.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    // Get contact information
    @GetMapping("/showContact/{contactId}")
    public Contacts showContact(@PathVariable String contactId) {
        return contactService.fetchContactById(contactId).get();
    }
}
