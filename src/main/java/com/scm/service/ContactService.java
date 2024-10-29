package com.scm.service;

import java.util.List;
import java.util.Optional;

import com.scm.entity.Contacts;
import com.scm.entity.Users;

public interface ContactService {

    public Contacts saveContact(Contacts contact);

    public Optional<Contacts> fetchContactById(String id);

    public List<Contacts> fetchAllContacts();

    public void deleteContactById(String id);

    public List<Contacts> fetchContactsByUserId(String userId);

    public List<Contacts> fetchAllContactsByUser(Users user);

    public Contacts updateContactById(Contacts contact);

}
