package com.vyatsu.practiceCSR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vyatsu.practiceCSR.entity.api.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

}