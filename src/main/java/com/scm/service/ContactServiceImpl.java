package com.scm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Contacts updateContact(Contacts contact) {
        return repo.save(contact);
    }

    @Override
    public Page<Contacts> fetchAllContactsByUser(Users user, int page, int pageSize, String sortField,
            String direction) {
        // Sorting
        Sort sort = Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return repo.findContactsByUser(user, pageable);
    }

}
