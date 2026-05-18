package com.example.Sync.repository;

import com.example.Sync.entity.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfo, Long> {

    // ✅ Find restaurant info by userId
    Optional<RestaurantInfo> findByUserId(Long userId);
}