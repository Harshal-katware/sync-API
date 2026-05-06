package com.example.Sync.service;

import com.example.Sync.entity.Order;
import com.example.Sync.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;

    public List<Order> getAll()                   { return repo.findAll(); }
    public List<Order> getByStatus(String status) { return repo.findByStatus(status); }

    public Optional<Order> getOpenByTable(Long tableId) {
        return repo.findByTableIdAndStatus(tableId, "OPEN");
    }

    // Frontend: POST /api/orders
    public Order create(Order order) {
        order.setStatus("OPEN");
        order.setCreatedAt(LocalDateTime.now());
        return repo.save(order);
    }

    // Frontend: PUT /api/orders/{id}  — update items/totals
    public Order update(Long id, Order updated) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.setItems(updated.getItems());
        order.setSubtotal(updated.getSubtotal());
        order.setDiscount(updated.getDiscount());
        order.setGst(updated.getGst());
        order.setServiceCharge(updated.getServiceCharge());
        order.setBillCharge(updated.getBillCharge());
        order.setTotal(updated.getTotal());
        return repo.save(order);
    }

    // Frontend: PUT /api/orders/{id}/settle — settle karo
    public Order settle(Long id, String paymentMode) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.setStatus("SETTLED");
        order.setPaymentMode(paymentMode);
        order.setSettledAt(LocalDateTime.now());
        return repo.save(order);
    }

    // Frontend: PUT /api/orders/{id}/save — KOT/Bill save
    public Order saveOrder(Long id) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.setStatus("SAVED");
        return repo.save(order);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}