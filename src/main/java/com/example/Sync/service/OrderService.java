package com.example.Sync.service;

import com.example.Sync.dto.AdjustmentDto;
import com.example.Sync.dto.OrderRequestDto;
import com.example.Sync.entity.Order;
import com.example.Sync.entity.OrderAdjustment;
import com.example.Sync.entity.OrderItem;
import com.example.Sync.repository.OrderAdjustmentRepository;
import com.example.Sync.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;

    // ✅ NEW
    private final OrderAdjustmentRepository adjustmentRepo;

    // ── GET ─────────────────────────────────────────────────────────

    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    public List<Order> getByStatus(String status) {
        return repo.findByStatus(status);
    }

    // ── CREATE ──────────────────────────────────────────────────────

    public Order createOrder(OrderRequestDto dto) {

        Order order = new Order();

        order.setTableId(dto.getTableId());

        order.setTableName(dto.getTableName());

        order.setSubtotal(dto.getSubtotal());

        order.setDiscount(dto.getDiscount());

        order.setGst(dto.getGst());

        order.setServiceCharge(dto.getServiceCharge());

        order.setBillCharge(dto.getBillCharge());

        order.setTotal(dto.getTotal());

        order.setStatus("OPEN");

        order.setCreatedAt(LocalDateTime.now());

        if (dto.getItems() != null) {

            List<OrderItem> items = dto.getItems()
                    .stream()
                    .map(i -> {

                        OrderItem item = new OrderItem();

                        item.setMenuId(i.getMenuId());

                        item.setName(i.getName());

                        item.setEmoji(i.getEmoji());

                        item.setPrice(i.getPrice());

                        item.setQty(i.getQty());

                        item.setOrder(order);

                        return item;

                    }).collect(Collectors.toList());

            order.setItems(items);
        }

        return repo.save(order);
    }

    // ── UPDATE ──────────────────────────────────────────────────────

    public Order updateOrder(Long id, OrderRequestDto dto) {

        Order order = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found: " + id));

        order.setSubtotal(dto.getSubtotal());

        order.setDiscount(dto.getDiscount());

        order.setGst(dto.getGst());

        order.setServiceCharge(dto.getServiceCharge());

        order.setBillCharge(dto.getBillCharge());

        order.setTotal(dto.getTotal());

        return repo.save(order);
    }

    // ── SETTLE ──────────────────────────────────────────────────────

    public Order settleOrder(Long id, String paymentMode) {

        Order order = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found: " + id));

        order.setStatus("SETTLED");

        order.setPaymentMode(paymentMode);

        order.setSettledAt(LocalDateTime.now());

        return repo.save(order);
    }

    // ── SAVE ────────────────────────────────────────────────────────

    public Order saveOrder(Long id) {

        Order order = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found: " + id));

        order.setStatus("SAVED");

        return repo.save(order);
    }

    // ── DELETE ──────────────────────────────────────────────────────

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // ── ORDER ADJUSTMENT ────────────────────────────────────────────

    public OrderAdjustment adjustOrder(
            Long orderId,
            AdjustmentDto dto
    ) {

        Order order = repo.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        OrderAdjustment adjustment =
                new OrderAdjustment();

        adjustment.setAmount(dto.getAmount());

        adjustment.setReason(dto.getReason());

        adjustment.setType(dto.getType());

        adjustment.setAdjustedBy(dto.getAdjustedBy());

        adjustment.setAdjustedAt(LocalDateTime.now());

        adjustment.setOrder(order);

        return adjustmentRepo.save(adjustment);
    }
}