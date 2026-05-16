package com.example.Sync.service;

import com.example.Sync.entity.KotItem;
import com.example.Sync.entity.Order;
import com.example.Sync.repository.KotItemRepository;
import com.example.Sync.repository.OrderRepository;
import com.example.Sync.dto.KotRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KotService {

    private final KotItemRepository kotItemRepository;
    private final OrderRepository   orderRepository;

    /**
     * Add a new KOT round to an existing order.
     * // add
     * Called when waiter clicks "Print KOT" AFTER the first time
     * (i.e., the order already exists in DB).
     *
     * Only the NEW items (with their incremental qty) are passed in.
     * We auto-increment the kotRound so kitchen knows this is a new ticket.
     *
     * @param orderId  the existing order's ID
     * @param request  DTO with the list of new items and their quantities
     * @return         list of saved KotItem entities (for this round)
     */
    @Transactional
    public List<KotItem> addKotRound(Long orderId, KotRequestDto request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        // Next round number = max existing round + 1
        int nextRound = kotItemRepository.findMaxKotRoundByOrderId(orderId) + 1;

        List<KotItem> kotItems = request.getItems().stream()
                .map(dto -> KotItem.builder()
                        .order(order)
                        .kotRound(nextRound)
                        .menuId(dto.getMenuId())
                        .name(dto.getName())
                        .emoji(dto.getEmoji())
                        .price(dto.getPrice())
                        .qty(dto.getQty())
                        .printedAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        return kotItemRepository.saveAll(kotItems);
    }

    /**
     * Save the first KOT for an order that was JUST created.
     * Used when the frontend creates the order and the first KOT in one shot.
     *
     * kotRound is always 1 here.
     */
    @Transactional
    public List<KotItem> saveFirstKot(Order order, KotRequestDto request) {
        List<KotItem> kotItems = request.getItems().stream()
                .map(dto -> KotItem.builder()
                        .order(order)
                        .kotRound(1)
                        .menuId(dto.getMenuId())
                        .name(dto.getName())
                        .emoji(dto.getEmoji())
                        .price(dto.getPrice())
                        .qty(dto.getQty())
                        .printedAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        return kotItemRepository.saveAll(kotItems);
    }

    /** Fetch all KOT history for an order (useful for kitchen display / review) */
    public List<KotItem> getKotHistory(Long orderId) {
        return kotItemRepository.findByOrderIdOrderByKotRoundAsc(orderId);
    }
}