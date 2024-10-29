package com.scm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entity.Contacts;
import com.scm.entity.Users;
import com.scm.repo.ContactRepo;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo repo;

    @Override
    public Contacts saveContact(Contacts contact) {
        String id = UUID.randomUUID().toString();
        contact.setCid(id);
        return repo.save(contact);
    }

    @Override
    public Optional<Contacts> fetchContactById(String id) {
        return repo.findById(id);
    }

    @Override
    public List<Contacts> fetchAllContacts() {
        return repo.findAll();
    }

    @Override
    public void deleteContactById(String id) {
        repo.deleteById(id);
    }

    @Override
    public List<Contacts> fetchContactsByUserId(String userId) {
        // return repo.fetchContactsByUserId(userId);
        return null;
    }

    @Override
    public Contacts updateContactById(Contacts contact) {
        return repo.save(contact);
    }

    @Override
    public List<Contacts> fetchAllContactsByUser(Users user) {
        return repo.findContactsByUser(user);
    }

}
