package com.vyatsu.practiceCSR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vyatsu.practiceCSR.entity.api.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.region.id = ?1 AND u.isActive = true AND u.isAdmin = false")
    List<User> findByRegionId(Long id);
}