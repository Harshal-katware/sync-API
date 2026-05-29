package com.example.Sync.repository;

import com.example.Sync.entity.OrderAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderAdjustmentRepository
        extends JpaRepository<OrderAdjustment, Long> {

    List<OrderAdjustment> findByOrderId(Long orderId);
}