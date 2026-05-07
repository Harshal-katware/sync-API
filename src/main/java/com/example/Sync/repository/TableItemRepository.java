package com.example.Sync.repository;

import com.example.Sync.entity.TableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableItemRepository extends JpaRepository<TableItem, Long> {}