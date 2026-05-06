package com.example.Sync.Repository;

import com.example.Sync.Entity.KotItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KotItemRepository extends JpaRepository<KotItem, Long> {

    List<KotItem> findByOrderIdOrderByKotRoundAsc(Long orderId);

    /** Get the latest KOT round number for an order (returns 0 if none yet) */
    @Query("SELECT COALESCE(MAX(k.kotRound), 0) FROM KotItem k WHERE k.order.id = :orderId")
    Integer findMaxKotRoundByOrderId(Long orderId);
}