package com.example.Sync.service;

import com.example.Sync.entity.User;
import com.example.Sync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserRepository repo;

    // ✅ Activate subscription
    public User activateSubscription(Long userId, String plan) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User.SubscriptionPlan subscriptionPlan =
                User.SubscriptionPlan.valueOf(plan);

        LocalDate start = LocalDate.now();
        LocalDate end;

        switch (subscriptionPlan) {
            case BASIC    -> end = start.plusMonths(3);
            case STANDARD -> end = start.plusMonths(6);
            case PREMIUM  -> end = start.plusYears(1);
            default       -> end = start.plusDays(7);
        }

        user.setSubscriptionStatus(User.SubscriptionStatus.ACTIVE);
        user.setSubscriptionPlan(subscriptionPlan);
        user.setSubscriptionStart(start);
        user.setSubscriptionEnd(end);

        return repo.save(user);
    }

    // ✅ Get all users (for super admin)
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // ✅ Check and expire subscriptions
    public void checkAndExpireSubscriptions() {
        List<User> users = repo.findAll();
        users.forEach(user -> {
            if (user.getSubscriptionEnd() != null &&
                    user.getSubscriptionEnd().isBefore(LocalDate.now()) &&
                    user.getSubscriptionStatus() != User.SubscriptionStatus.EXPIRED) {
                user.setSubscriptionStatus(User.SubscriptionStatus.EXPIRED);
                repo.save(user);
            }
        });
    }

    // ✅ Deactivate subscription
    public User deactivateSubscription(Long userId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setSubscriptionStatus(User.SubscriptionStatus.EXPIRED);
        return repo.save(user);
    }
}