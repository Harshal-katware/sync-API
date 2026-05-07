package com.example.Sync.service;

import com.example.Sync.entity.RestaurantInfo;
import com.example.Sync.repository.RestaurantInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantInfoService {

    private final RestaurantInfoRepository repository;

    public RestaurantInfoService(RestaurantInfoRepository repository) {
        this.repository = repository;
    }

    public RestaurantInfo getInfo() {
        return repository.findAll()
                .stream()
                .findFirst()
                .orElse(new RestaurantInfo());
    }

    public RestaurantInfo saveInfo(RestaurantInfo info) {
        // Always find the first record and update it
        return repository.findAll()
                .stream()
                .findFirst()
                .map(existing -> {
                    existing.setName(info.getName());
                    existing.setEmail(info.getEmail());
                    existing.setPhone(info.getPhone());
                    existing.setAddress(info.getAddress());
                    existing.setGst(info.getGst());
                    existing.setFssai(info.getFssai());
                    existing.setWebsite(info.getWebsite());
                    return repository.save(existing);
                })
                .orElseGet(() -> repository.save(info));
    }
}