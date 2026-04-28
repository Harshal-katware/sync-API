package com.example.Sync.Repository;

import com.example.Sync.Entity.TableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TableItemRepository extends JpaRepository<TableItem, Long> {
    List<TableItem> findByZone(String zone);
}