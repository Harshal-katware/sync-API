package com.example.Sync.service;

import com.example.Sync.entity.OperatingHours;
import com.example.Sync.repository.OperatingHoursRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperatingHoursService {

    private final OperatingHoursRepository repository;

    public OperatingHoursService(OperatingHoursRepository repository) {
        this.repository = repository;
    }

    public List<OperatingHours> getAllHours() {
        return repository.findAll();
    }

    public OperatingHours updateHours(Long id, OperatingHours updatedHours) {
        OperatingHours existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hours not found"));
        existing.setOpen(updatedHours.getOpen());
        existing.setClose(updatedHours.getClose());
        existing.setClosed(updatedHours.getClosed());
        return repository.save(existing);
    }

    public List<OperatingHours> saveAll(List<OperatingHours> hours) {
        return repository.saveAll(hours);
    }
}