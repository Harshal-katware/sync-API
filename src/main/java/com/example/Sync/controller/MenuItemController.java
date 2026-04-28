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

    private final MenuItemService service;

    @GetMapping
    public List<MenuItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/category/{cat}")
    public List<MenuItem> getByCategory(@PathVariable String cat) {
        return service.getByCategory(cat);
    }

    @PostMapping
    public MenuItem create(@RequestBody MenuItem item) {
        return service.create(item);
    }

    @PutMapping("/{id}")
    public MenuItem update(@PathVariable Long id, @RequestBody MenuItem item) {
        return service.update(id, item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}