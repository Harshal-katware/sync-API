package com.example.Sync.repository;

import com.example.Sync.entity.Captain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaptainRepository extends JpaRepository<Captain, Long> {
    List<Captain> findAllByOrderByIdAsc();
    List<Captain> findByActiveTrue();
}