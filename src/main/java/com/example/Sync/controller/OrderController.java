package com.example.Sync.controller;

import com.example.Sync.entity.Order;
import com.example.Sync.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    // GET /api/orders
    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    // GET /api/orders/status/OPEN
    @GetMapping("/status/{status}")
    public List<Order> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    // GET /api/orders/table/3/open
    @GetMapping("/table/{tableId}/open")
    public ResponseEntity<Order> getOpenOrder(@PathVariable Long tableId) {
        return service.getOpenByTable(tableId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/orders  →  { tableId, tableName, items[], subtotal, discount, gst, ... }
    @PostMapping
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    // PUT /api/orders/{id}  →  update items / totals
    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        return service.update(id, order);
    }

    // PUT /api/orders/{id}/settle  →  { "paymentMode": "CASH" }
    @PutMapping("/{id}/settle")
    public Order settle(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        return service.settle(id, body.get("paymentMode"));
    }

    // PUT /api/orders/{id}/save  →  Save KOT / Bill
    @PutMapping("/{id}/save")
    public Order save(@PathVariable Long id) {
        return service.saveOrder(id);
    }

    // DELETE /api/orders/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}