package com.example.Sync.Service;

import com.example.Sync.Entity.TableItem;
import com.example.Sync.Repository.TableItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableItemService {

    private final TableItemRepository repo;

    public List<TableItem> getAll()               { return repo.findAll(); }
    public List<TableItem> getByZone(String zone) { return repo.findByZone(zone); }
    public TableItem       create(TableItem t)    { return repo.save(t); }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}