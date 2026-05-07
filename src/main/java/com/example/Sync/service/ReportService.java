package com.example.Sync.service;

import com.example.Sync.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrderRepository orderRepo;

    // ── Today's Report ──────────────────────────────────────────────
    public Map<String, Object> getDailyReport(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = date.plusDays(1).atStartOfDay();

        List<Object[]> rows = orderRepo.findSettledOrdersBetween(start, end);

        double totalSales = 0, discount = 0, gst = 0;
        int orders = 0;
        Map<String, double[]> productMap = new LinkedHashMap<>();
        Map<String, Double>   paymentMap = new LinkedHashMap<>();

        for (Object[] row : rows) {
            double total   = toDouble(row[1]);
            double disc    = toDouble(row[2]);
            double gstAmt  = toDouble(row[3]);
            String payment = String.valueOf(row[4]);
            String item    = String.valueOf(row[5]);
            double qty     = toDouble(row[6]);
            double price   = toDouble(row[7]);

            totalSales += total;
            discount   += disc;
            gst        += gstAmt;
            orders++;

            productMap.computeIfAbsent(item, k -> new double[]{0, 0});
            productMap.get(item)[0] += qty;
            productMap.get(item)[1] += qty * price;

            paymentMap.merge(payment, total, Double::sum);
        }

        double net = totalSales - discount;
        int avgOrder = orders > 0 ? (int)(totalSales / orders) : 0;

        List<Map<String, Object>> products = new ArrayList<>();
        productMap.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue()[0], a.getValue()[0]))
                .limit(10)
                .forEach(e -> {
                    Map<String, Object> p = new LinkedHashMap<>();
                    p.put("name",    e.getKey());
                    p.put("qty",     (int) e.getValue()[0]);
                    p.put("revenue", (int) e.getValue()[1]);
                    products.add(p);
                });

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalSales", (int) totalSales);
        result.put("discount",   (int) discount);
        result.put("refund",     0);
        result.put("gst",        (int) gst);
        result.put("netSales",   (int) net);
        result.put("orders",     orders);
        result.put("avgOrder",   avgOrder);
        result.put("products",   products);
        result.put("payment",    paymentMap);
        result.put("date",       date.toString());
        return result;
    }

    // ── Monthly Report ──────────────────────────────────────────────
    public Map<String, Object> getMonthlyReport(int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end   = start.plusMonths(1);

        List<Object[]> rows = orderRepo.findSettledOrdersBetween(
                start.atStartOfDay(), end.atStartOfDay());

        double totalSales = 0, discount = 0;
        int orders = 0;
        Map<String, double[]> productMap = new LinkedHashMap<>();
        Map<String, Double>   paymentMap = new LinkedHashMap<>();

        for (Object[] row : rows) {
            double total   = toDouble(row[1]);
            double disc    = toDouble(row[2]);
            String payment = String.valueOf(row[4]);
            String item    = String.valueOf(row[5]);
            double qty     = toDouble(row[6]);
            double price   = toDouble(row[7]);

            totalSales += total;
            discount   += disc;
            orders++;

            productMap.computeIfAbsent(item, k -> new double[]{0, 0});
            productMap.get(item)[0] += qty;
            productMap.get(item)[1] += qty * price;

            paymentMap.merge(payment, total, Double::sum);
        }

        double net = totalSales - discount;
        int avgOrder = orders > 0 ? (int)(totalSales / orders) : 0;

        List<Map<String, Object>> topItems = new ArrayList<>();
        productMap.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue()[0], a.getValue()[0]))
                .limit(10)
                .forEach(e -> {
                    Map<String, Object> p = new LinkedHashMap<>();
                    p.put("name", e.getKey());
                    p.put("qty",  (int) e.getValue()[0]);
                    topItems.add(p);
                });

        // ✅ Fix — Double.doubleValue() use karke int mein convert karo
        Map<String, Object> payment = new LinkedHashMap<>();
        payment.put("cash",   (int) paymentMap.getOrDefault("CASH",   0.0).doubleValue());
        payment.put("upi",    (int) paymentMap.getOrDefault("UPI",    0.0).doubleValue());
        payment.put("card",   (int) paymentMap.getOrDefault("CARD",   0.0).doubleValue());
        payment.put("online", (int) paymentMap.getOrDefault("ONLINE", 0.0).doubleValue());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalSales", (int) totalSales);
        result.put("discount",   (int) discount);
        result.put("refund",     0);
        result.put("netSales",   (int) net);
        result.put("orders",     orders);
        result.put("avgOrder",   avgOrder);
        result.put("topItems",   topItems);
        result.put("payment",    payment);
        result.put("month",      month);
        result.put("year",       year);
        return result;
    }

    // ── Top Products ────────────────────────────────────────────────
    public Map<String, Object> getTopProducts(int limit) {
        List<Object[]> rows = orderRepo.findAllSettledOrders();

        Map<String, double[]> productMap = new LinkedHashMap<>();

        for (Object[] row : rows) {
            String item  = String.valueOf(row[5]);
            double qty   = toDouble(row[6]);
            double price = toDouble(row[7]);

            productMap.computeIfAbsent(item, k -> new double[]{0, 0});
            productMap.get(item)[0] += qty;
            productMap.get(item)[1] += qty * price;
        }

        List<Map<String, Object>> products = new ArrayList<>();
        productMap.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue()[0], a.getValue()[0]))
                .limit(limit)
                .forEach(e -> {
                    Map<String, Object> p = new LinkedHashMap<>();
                    p.put("name",    e.getKey());
                    p.put("qty",     (int) e.getValue()[0]);
                    p.put("revenue", (int) e.getValue()[1]);
                    products.add(p);
                });

        int totalQty     = products.stream().mapToInt(p -> (int) p.get("qty")).sum();
        int totalRevenue = products.stream().mapToInt(p -> (int) p.get("revenue")).sum();
        String topProduct = products.isEmpty() ? "-" : (String) products.get(0).get("name");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("products",     products);
        result.put("totalQty",     totalQty);
        result.put("totalRevenue", totalRevenue);
        result.put("topProduct",   topProduct);
        return result;
    }

    // ── Custom Report ────────────────────────────────────────────────
    public Map<String, Object> getCustomReport(
            String payment, String orderType,
            LocalDate from, LocalDate to) {

        LocalDateTime start = from != null ? from.atStartOfDay()           : LocalDate.of(2000, 1, 1).atStartOfDay();
        LocalDateTime end   = to   != null ? to.plusDays(1).atStartOfDay() : LocalDate.now().plusDays(1).atStartOfDay();

        List<Object[]> rows = orderRepo.findSettledOrdersBetween(start, end);

        List<Map<String, Object>> items = new ArrayList<>();
        double totalSales = 0;
        int totalQty = 0;

        for (Object[] row : rows) {
            String pay  = String.valueOf(row[4]);
            String type = row[8] != null ? String.valueOf(row[8]) : "dine-in";
            String name = String.valueOf(row[5]);
            double qty  = toDouble(row[6]);
            double rev  = toDouble(row[1]);

            boolean payMatch  = payment   == null || payment.equalsIgnoreCase("all")   || pay.equalsIgnoreCase(payment);
            boolean typeMatch = orderType == null || orderType.equalsIgnoreCase("all") || type.equalsIgnoreCase(orderType);

            if (payMatch && typeMatch) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("name",    name);
                item.put("qty",     (int) qty);
                item.put("revenue", (int) rev);
                item.put("payment", pay);
                item.put("type",    type);
                items.add(item);
                totalSales += rev;
                totalQty   += (int) qty;
            }
        }

        String topProduct = items.stream()
                .max(Comparator.comparingInt(i -> (int) i.get("qty")))
                .map(i -> (String) i.get("name"))
                .orElse("-");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("items",       items);
        result.put("totalSales",  (int) totalSales);
        result.put("totalQty",    totalQty);
        result.put("topProduct",  topProduct);
        return result;
    }

    // ── Helper ───────────────────────────────────────────────────────
    private double toDouble(Object val) {
        if (val == null) return 0;
        if (val instanceof Number) return ((Number) val).doubleValue();
        try { return Double.parseDouble(val.toString()); }
        catch (Exception e) { return 0; }
    }
}