package com.example.Sync.Service;

import com.example.Sync.Entity.Order;
import com.example.Sync.Entity.OrderItem;
import com.example.Sync.Repository.OrderRepository;
import com.example.Sync.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;

    // ── GET ───────────────────────────────────────────────────────────────────

    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    public List<Order> getByStatus(String status) {
        return repo.findByStatus(status);
    }

    // ── CREATE ────────────────────────────────────────────────────────────────

    public Order createOrder(OrderRequestDto dto) {
        Order order = new Order();                       // ✅ Long nahi, Order object
        order.setTableId(dto.getTableId());              // ✅ equals() nahi, setTableId()
        order.setTableName(dto.getTableName());          // ✅ getClass() nahi, setTableName()
        order.setSubtotal(dto.getSubtotal());
        order.setDiscount(dto.getDiscount());
        order.setGst(dto.getGst());
        order.setServiceCharge(dto.getServiceCharge());
        order.setBillCharge(dto.getBillCharge());
        order.setTotal(dto.getTotal());
        order.setStatus("OPEN");
        order.setCreatedAt(LocalDateTime.now());

        if (dto.getItems() != null) {
            List<OrderItem> items = dto.getItems().stream().map(i -> {
                OrderItem item = new OrderItem();
                item.setMenuId(i.getMenuId());
                item.setName(i.getName());
                item.setEmoji(i.getEmoji());
                item.setPrice(i.getPrice());
                item.setQty(i.getQty());
                item.setOrder(order);                    // ✅ setId(order) nahi, setOrder(order)
                return item;
            }).collect(Collectors.toList());
            order.setItems(items);                       // ✅ ((Order) order) cast nahi chahiye
        }

        return repo.save(order);
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    public Order updateOrder(Long id, OrderRequestDto dto) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.setSubtotal(dto.getSubtotal());
        order.setDiscount(dto.getDiscount());
        order.setGst(dto.getGst());
        order.setServiceCharge(dto.getServiceCharge());
        order.setBillCharge(dto.getBillCharge());
        order.setTotal(dto.getTotal());
        return repo.save(order);
    }

    // ── SETTLE ────────────────────────────────────────────────────────────────

    public Order settleOrder(Long id, String paymentMode) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.setStatus("SETTLED");
        order.setPaymentMode(paymentMode);
        order.setSettledAt(LocalDateTime.now());
        return repo.save(order);
    }

    // ── SAVE ──────────────────────────────────────────────────────────────────

    public Order saveOrder(Long id) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.setStatus("SAVED");
        return repo.save(order);
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    public void delete(Long id) {
        repo.deleteById(id);
    }
}