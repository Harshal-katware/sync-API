package com.example.Sync.Controller;

import com.example.Sync.Entity.TableItem;
import com.example.Sync.Service.TableItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableItemController {

    private final TableItemService service;

    @GetMapping
    public List<TableItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/zone/{zone}")
    public List<TableItem> getByZone(@PathVariable String zone) {
        return service.getByZone(zone);
    }

    @PostMapping
    public TableItem create(@RequestBody TableItem table) {
        return service.create(table);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}