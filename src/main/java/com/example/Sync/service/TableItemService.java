package com.example.Sync.service;

import com.example.Sync.entity.TableItem;
import com.example.Sync.repository.TableItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableItemService {

    private final TableItemRepository tableItemRepository;

    public List<TableItem> getAll() {
        return tableItemRepository.findAll();
    }

    public TableItem add(TableItem t) {
        return tableItemRepository.save(t);
    }

    public void delete(Long id) {
        tableItemRepository.deleteById(id);
    }
}