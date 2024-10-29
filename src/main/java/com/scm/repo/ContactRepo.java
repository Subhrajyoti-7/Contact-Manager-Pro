package com.scm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entity.Contacts;
import com.scm.entity.Users;

@Repository
public interface ContactRepo extends JpaRepository<Contacts, String> {

    // public List<Contacts> fetchContactsByUser(Users user);
    public List<Contacts> findContactsByUser(Users user);

    @Query("SELECT c FROM Contacts c WHERE c.user.id = :userId")
    // @Query("SELECT c FROM contacts AS c INNER JOIN users AS p ON c.cid = p.uid
    // WHERE p.uid = userId")
    public List<Contacts> fetchContactsByUserId(@Param("userId") String userId);

}
