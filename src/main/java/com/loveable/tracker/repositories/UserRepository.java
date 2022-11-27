package com.loveable.tracker.repositories;

import com.loveable.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findUsersByEmailAndPassword(String email, String password);

    Optional<User> findByUuid(String userUuid);

}
