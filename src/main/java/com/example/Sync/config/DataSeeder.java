package com.example.Sync.Config;

import com.example.Sync.Entity.MenuItem;
import com.example.Sync.Entity.TableItem;
import com.example.Sync.Repository.MenuItemRepository;
import com.example.Sync.Repository.TableItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MenuItemRepository  menuRepo;
    private final TableItemRepository tableRepo;

    @Override
    public void run(String... args) {

        // ── Menu Items ──────────────────────────────────────────
        if (menuRepo.count() == 0) {
            menuRepo.saveAll(List.of(
                    menu("Butter Milk",        30.0,  "drink",  "🥛"),
                    menu("Butter Kulcha",      50.0,  "veg",    "🫓"),
                    menu("Kaju Paneer Masala", 220.0, "veg",    "🧆"),
                    menu("Dal Makhani",        180.0, "veg",    "🍲"),
                    menu("Chicken Tikka",      280.0, "nonveg", "🍗"),
                    menu("Mutton Curry",       320.0, "nonveg", "🍛"),
                    menu("Roti",               15.0,  "veg",    "🫓"),
                    menu("Naan",               25.0,  "veg",    "🫓"),
                    menu("Lassi",              60.0,  "drink",  "🥤"),
                    menu("Cold Drink",         40.0,  "drink",  "🧃"),
                    menu("Paneer Tikka",       200.0, "veg",    "🧀"),
                    menu("Veg Biryani",        160.0, "veg",    "🍚"),
                    menu("Chicken Biryani",    240.0, "nonveg", "🍚"),
                    menu("Raita",              40.0,  "veg",    "🥣"),
                    menu("Gulab Jamun",        50.0,  "veg",    "🍮")
            ));
            System.out.println("✅ Menu seeded!");
        }

        // ── Tables ──────────────────────────────────────────────
        if (tableRepo.count() == 0) {
            for (int i = 1;  i <= 11; i++) tableRepo.save(table("Table " + i, "HALL"));
            for (int i = 12; i <= 16; i++) tableRepo.save(table("Table " + i, "FAMILY"));
            for (int i = 31; i <= 34; i++) tableRepo.save(table("Bill "  + i, "PARCEL"));
            System.out.println("✅ Tables seeded!");
        }
    }

    private MenuItem menu(String name, Double price, String category, String emoji) {
        MenuItem m = new MenuItem();
        m.setName(name);
        m.setPrice(price);
        m.setCategory(category);
        m.setEmoji(emoji);
        return m;
    }

    private TableItem table(String name, String zone) {
        TableItem t = new TableItem();
        t.setName(name);
        t.setZone(zone);
        return t;
    }
}