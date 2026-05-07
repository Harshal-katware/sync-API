package com.example.Sync.service;

import com.example.Sync.entity.MenuItem;
import com.example.Sync.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAllItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem addItem(MenuItem item) {
        return menuItemRepository.save(item);
    }

    public MenuItem updateItem(Long id, MenuItem updatedItem) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        existing.setName(updatedItem.getName());
        existing.setPrice(updatedItem.getPrice());
        existing.setCategory(updatedItem.getCategory());

        return menuItemRepository.save(existing);
    }

    public void deleteItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}