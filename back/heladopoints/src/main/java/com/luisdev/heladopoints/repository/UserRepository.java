package com.luisdev.heladopoints.repository;

import com.luisdev.heladopoints.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String username);
    Optional<User> findOptionalByEmail(String login);
    boolean existsByEmail(String email);
}
