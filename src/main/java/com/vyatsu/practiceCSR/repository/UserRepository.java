package com.vyatsu.practiceCSR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vyatsu.practiceCSR.entity.api.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}