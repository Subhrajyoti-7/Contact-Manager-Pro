package com.scm.service;

import java.util.List;
import java.util.Optional;

import com.scm.entity.Users;

public interface UserService {

    Users saveUser(Users user);

    Optional<Users> getUserById(String id);

    List<Users> getAllUsers();

    void deleteUserById(String id);

    Users updateUser(Users user);

    Users updatePassword(Users user);

    Optional<Users> findByEmail(String email);

    boolean isUserExist(String id);

    boolean isUserExistByEmail(String email);

    Optional<Users> getEmailByProviderId(String providerId);

    Optional<Users> getUserByToken(String token);

}
