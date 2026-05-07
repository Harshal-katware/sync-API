package com.example.Sync.controller;

import com.example.Sync.entity.TableItem;
import com.example.Sync.service.TableItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableItemController {

    private final TableItemService tableItemService;

    @GetMapping
    public List<TableItem> getAll() {
        return tableItemService.getAll();
    }

    @PostMapping
    public TableItem add(@RequestBody TableItem t) {
        return tableItemService.add(t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tableItemService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}