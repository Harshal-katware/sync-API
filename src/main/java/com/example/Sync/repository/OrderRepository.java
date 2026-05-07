package com.example.Sync.repository;

import com.example.Sync.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order>     findByStatus(String status);
    Optional<Order> findByTableIdAndStatus(Long tableId, String status);
}