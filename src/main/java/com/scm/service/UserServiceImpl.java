package com.scm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.config.SecurityConfig;
import com.scm.entity.Users;
import com.scm.helper.AppConstants;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    public Users saveUser(Users user) {
        // Generate the uid
        String userId = UUID.randomUUID().toString();

        // Encoding the password
        String encodedPassword = securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Set the user roles
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        user.setUid(userId);

        return userRepo.save(user);
    }

    @Override
    public Optional<Users> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUserById(String id) {
        Users user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user);

    }

    @Override
    public Users updateUser(Users user) {
        Users users = userRepo.findById(user.getUid())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        users.setName(user.getName());
        users.setEmail(user.getEmail());
        users.setPhoneNumber(user.getPhoneNumber());
        users.setAbout(user.getAbout());
        users.setPassword(user.getPassword());
        users.setProvider(user.getProvider());
        return userRepo.save(users);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public boolean isUserExist(String id) {
        return userRepo.findById(id).isPresent();
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    @Override
    public Optional<Users> getEmailByProviderId(String providerId) {
        return userRepo.findByProviderId(providerId);
    }

}
