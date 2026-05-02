package com.example.Sync.Service;

import com.example.Sync.Entity.TableItem;
import com.example.Sync.Repository.TableItemRepository;
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