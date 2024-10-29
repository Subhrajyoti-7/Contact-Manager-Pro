package com.scm.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {
    // Additional custom methods here if needed
    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndPassword(String email, String password);

    Optional<Users> findByProviderId(String providerId);

}
