package com.example.Sync.Repository;

import com.example.Sync.Entity.TableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableItemRepository extends JpaRepository<TableItem, Long> {}