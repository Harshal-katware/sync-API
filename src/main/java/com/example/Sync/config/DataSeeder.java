package com.example.Sync.config;

import com.example.Sync.repository.TableItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TableItemRepository tableRepo;

    @Override
    public void run(String... args) {
        // No auto-seeding — tables are managed manually via the UI
    }
}