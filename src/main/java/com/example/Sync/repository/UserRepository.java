package com.example.Sync.repository;

import com.example.Sync.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByContactNumber(String contactNumber);

    Optional<User> findByResetToken(String token);

    @Query("SELECT u FROM User u WHERE u.email = :input OR u.contactNumber = :input")
    Optional<User> findByEmailOrContact(@Param("input") String input);

    // ✅ Find expired subscriptions
    @Query("SELECT u FROM User u WHERE u.subscriptionEnd < :today AND u.subscriptionStatus != com.example.Sync.entity.User.SubscriptionStatus.EXPIRED")
    List<User> findExpiredSubscriptions(@Param("today") LocalDate today);
}