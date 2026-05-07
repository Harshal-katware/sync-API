package com.example.Sync.repository;

import com.example.Sync.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // ✅ Pehle se the — touch nahi kiya
    List<Order>     findByStatus(String status);
    Optional<Order> findByTableIdAndStatus(Long tableId, String status);

    // ✅ Reports ke liye add kiya — date range se settled orders
    @Query("""
        SELECT o.id, o.total, o.discount, o.gst, o.paymentMode,
               i.name, i.qty, i.price, o.orderType
        FROM Order o
        JOIN o.items i
        WHERE o.status = 'SETTLED'
          AND o.createdAt >= :start
          AND o.createdAt < :end
        ORDER BY o.createdAt DESC
    """)
    List<Object[]> findSettledOrdersBetween(
            @Param("start") LocalDateTime start,
            @Param("end")   LocalDateTime end
    );

    // ✅ Reports ke liye add kiya — sab time ke settled orders (Top Products)
    @Query("""
        SELECT o.id, o.total, o.discount, o.gst, o.paymentMode,
               i.name, i.qty, i.price, o.orderType
        FROM Order o
        JOIN o.items i
        WHERE o.status = 'SETTLED'
        ORDER BY o.createdAt DESC
    """)
    List<Object[]> findAllSettledOrders();
}