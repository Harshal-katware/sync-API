package com.example.Sync.Controller;

import com.example.Sync.Entity.MenuItem;
import com.example.Sync.Service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public List<MenuItem> getAllItems() {
        return menuItemService.getAllItems();
    }

    @PostMapping
    public MenuItem addItem(@RequestBody MenuItem item) {
        return menuItemService.addItem(item);
    }

    @PutMapping("/{id}")
    public MenuItem updateItem(@PathVariable Long id, @RequestBody MenuItem item) {
        return menuItemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        menuItemService.deleteItem(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}