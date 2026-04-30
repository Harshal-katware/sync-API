package com.example.Sync.repository;

import com.example.Sync.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByContactNumber(String contactNumber);

    // ✅ Email ya contact number dono se dhundho
    @Query("SELECT u FROM User u WHERE u.email = :input OR u.contactNumber = :input")
    Optional<User> findByEmailOrContact(@Param("input") String input);
}