package com.scm.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scm.config.SecurityConfig;
import com.scm.entity.Users;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SecurityConfig securityConfig;

    @Value("${server.baseUrl}")
    private String baseUrl;

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

        // Sending Email verification
        String emailToken = UUID.randomUUID().toString();
        String emailLink = Helper.getLinkForEmailVerification(emailToken, baseUrl);
        user.setEmailToken(emailToken);
        Users savedUser = userRepo.save(user);
        emailService.sendEmail(savedUser.getEmail(), "Verify Account : Contact Manager Pro",
                "Click the below link to verify yourself\n" + emailLink);

        return savedUser;
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
        return userRepo.save(user);
    }

    @Override
    public Users updatePassword(Users user) {
        // Encoding the password
        String encodedPassword = securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
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

    @Override
    public Optional<Users> getUserByToken(String token) {
        return userRepo.findByEmailToken(token);
    }

}
