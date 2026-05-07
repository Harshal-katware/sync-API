package com.example.Sync.service;

import com.example.Sync.entity.Tax;
import com.example.Sync.repository.TaxRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaxService {

    private final TaxRepository taxRepository;

    public TaxService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    public List<Tax> getAllTaxes() {
        return taxRepository.findAll();
    }

    public Tax addTax(Tax tax) {
        return taxRepository.save(tax);
    }

    public Tax updateTax(Long id, Tax updatedTax) {
        Tax existing = taxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tax not found"));
        existing.setName(updatedTax.getName());
        existing.setRate(updatedTax.getRate());
        existing.setEnabled(updatedTax.getEnabled());
        return taxRepository.save(existing);
    }

    public void deleteTax(Long id) {
        taxRepository.deleteById(id);
    }
}