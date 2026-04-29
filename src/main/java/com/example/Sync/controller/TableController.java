package com.example.Sync.controller;

import com.example.Sync.entity.TableEntity;
import com.example.Sync.service.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin(origins = "http://localhost:5173")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public List<TableEntity> getAllTables() {
        return tableService.getAllTables();
    }

    @PostMapping
    public TableEntity addTable(@RequestBody TableEntity table) {
        return tableService.addTable(table);
    }

    @PutMapping("/{id}")
    public TableEntity updateTable(@PathVariable Long id, @RequestBody TableEntity table) {
        return tableService.updateTable(id, table);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}