package com.example.Sync.service;

import com.example.Sync.entity.TableEntity;
import com.example.Sync.repository.TableRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<TableEntity> getAllTables() {
        return tableRepository.findAll();
    }

    public TableEntity addTable(TableEntity table) {
        return tableRepository.save(table);
    }

    public TableEntity updateTable(Long id, TableEntity updatedTable) {
        TableEntity existing = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        existing.setNumber(updatedTable.getNumber());
        existing.setCapacity(updatedTable.getCapacity());
        existing.setType(updatedTable.getType());
        existing.setActive(updatedTable.getActive());
        return tableRepository.save(existing);
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }
}