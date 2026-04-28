package com.example.Sync.Service;

import com.example.Sync.Entity.MenuItem;
import com.example.Sync.Repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository repo;

    public List<MenuItem> getAll() {
        return repo.findAll();
    }

    public List<MenuItem> getByCategory(String category) {
        return repo.findByCategory(category);
    }

    public MenuItem create(MenuItem item) {
        return repo.save(item);
    }

    public MenuItem update(Long id, MenuItem updated) {
        MenuItem item = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found: " + id));
        item.setName(updated.getName());
        item.setPrice(updated.getPrice());
        item.setCategory(updated.getCategory());
        item.setEmoji(updated.getEmoji());
        return repo.save(item);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}