package com.example.Sync.controller;

import com.example.Sync.entity.KotItem;
import com.example.Sync.entity.Order;
import com.example.Sync.service.KotService;
import com.example.Sync.service.OrderService;
import com.example.Sync.dto.KotRequestDto;
import com.example.Sync.dto.OrderRequestDto;
import com.example.Sync.dto.SettleRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
// ✅ @CrossOrigin hata diya — ab WebConfig.java globally handle karega
public class OrderController {

    private final OrderService orderService;
    private final KotService   kotService;

    // ── Create Order + Save First KOT ────────────────────────────────────────
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto dto) {
        // ✅ FIX: createOrder(dto) call karo — saveOrder(tableId) nahi
        Order order = orderService.createOrder(dto);

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            KotRequestDto kotRequest = new KotRequestDto();
            kotRequest.setItems(
                    dto.getItems().stream().map(item -> {
                        KotRequestDto.KotItemDto k = new KotRequestDto.KotItemDto();
                        k.setMenuId(item.getMenuId());
                        k.setName(item.getName());
                        k.setEmoji(item.getEmoji());
                        k.setPrice(item.getPrice());
                        k.setQty(item.getQty());
                        return k;
                    }).toList()
            );
            kotService.saveFirstKot(order, kotRequest);
        }

        return ResponseEntity.ok(order);
    }

    // ── Update Order totals ───────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDto dto) {
        // ✅ FIX: updateOrder(id, dto) call karo — saveOrder(id) nahi
        Order order = orderService.updateOrder(id, dto);
        return ResponseEntity.ok(order);
    }

    // ── Settle Bill ───────────────────────────────────────────────────────────
    @PutMapping("/{id}/settle")
    public ResponseEntity<Order> settleOrder(@PathVariable Long id, @RequestBody SettleRequestDto dto) {
        // ✅ FIX: settleOrder(id, paymentMode) call karo — saveOrder(id) nahi
        Order order = orderService.settleOrder(id, dto.getPaymentMode());
        return ResponseEntity.ok(order);
    }

    // ── Save (mark as SAVED without settling) ────────────────────────────────
    @PutMapping("/{id}/save")
    public ResponseEntity<Order> saveOrder(@PathVariable Long id) {
        Order order = orderService.saveOrder(id);
        return ResponseEntity.ok(order);
    }

    // ── Add KOT round (2nd, 3rd KOT for same table) ──────────────────────────
    @PostMapping("/{id}/kot")
    public ResponseEntity<List<KotItem>> addKotRound(
            @PathVariable Long id,
            @RequestBody KotRequestDto request
    ) {
        List<KotItem> saved = kotService.addKotRound(id, request);
        return ResponseEntity.ok(saved);
    }

    // ── Get KOT history for an order ─────────────────────────────────────────
    @GetMapping("/{id}/kot")
    public ResponseEntity<List<KotItem>> getKotHistory(@PathVariable Long id) {
        return ResponseEntity.ok(kotService.getKotHistory(id));
    }
}