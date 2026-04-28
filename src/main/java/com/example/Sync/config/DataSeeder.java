package com.example.Sync.Config;

import com.example.Sync.Entity.TableItem;
import com.example.Sync.Repository.TableItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TableItemRepository tableRepo;

    @Override
    public void run(String... args) {

        if (tableRepo.count() == 0) {
            for (int i = 1;  i <= 11; i++) tableRepo.save(table("Table " + i, "HALL"));
            for (int i = 12; i <= 16; i++) tableRepo.save(table("Table " + i, "FAMILY"));
            for (int i = 31; i <= 34; i++) tableRepo.save(table("Bill "  + i, "PARCEL"));
            System.out.println("Tables seeded!");
        }
    }

    private TableItem table(String name, String zone) {
        TableItem t = new TableItem();
        t.setName(name);
        t.setZone(zone);
        return t;
    }
}