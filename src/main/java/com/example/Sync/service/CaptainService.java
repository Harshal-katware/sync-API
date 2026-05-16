package com.example.Sync.service;

import com.example.Sync.entity.Captain;
import com.example.Sync.repository.CaptainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaptainService {

    private final CaptainRepository repo;

    /** Sabhi captains — settings page ke liye */
    public List<Captain> getAll() {
        return repo.findAllByOrderByIdAsc();
    }

    /** Sirf active captains — POS dropdown ke liye */
    public List<Captain> getActive() {
        return repo.findByActiveTrue();
    }

    /** Naya captain add karo */
    public Captain add(Captain captain) {
        // Name null/empty check
        if (captain.getName() == null || captain.getName().trim().isEmpty()) {
            throw new RuntimeException("Captain name cannot be empty");
        }
        captain.setName(captain.getName().trim());

        // Phone empty string to null
        if (captain.getPhone() != null && captain.getPhone().trim().isEmpty()) {
            captain.setPhone(null);
        } else if (captain.getPhone() != null) {
            captain.setPhone(captain.getPhone().trim());
        }

        // Always force active = true for new captain
        captain.setActive(true);

        return repo.save(captain);
    }

    /** Captain update karo */
    public Captain update(Long id, Captain incoming) {
        Captain existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Captain not found: " + id));

        if (incoming.getName() != null && !incoming.getName().trim().isEmpty()) {
            existing.setName(incoming.getName().trim());
        }

        if (incoming.getPhone() == null || incoming.getPhone().trim().isEmpty()) {
            existing.setPhone(null);
        } else {
            existing.setPhone(incoming.getPhone().trim());
        }

        if (incoming.getActive() != null) {
            existing.setActive(incoming.getActive());
        }

        return repo.save(existing);
    }

    /** Captain delete karo */
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Captain not found: " + id);
        }
        repo.deleteById(id);
    }
}