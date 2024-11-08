package com.scm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.scm.entity.Contacts;
import com.scm.entity.Users;

public interface ContactService {

    public Contacts saveContact(Contacts contact);

    public Optional<Contacts> fetchContactById(String id);

    public List<Contacts> fetchAllContacts();

    public void deleteContactById(String id);

    public List<Contacts> fetchContactsByUserId(String userId);

    public Page<Contacts> fetchAllContactsByUser(Users user, int page, int pageSize, String sortField,
            String direction);

    public Contacts updateContact(Contacts contact);

}
