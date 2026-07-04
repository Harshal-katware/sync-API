package com.example.Sync.dto;

import java.util.List;

public class PrintRequest {

    private Integer tableId;
    private String  tableName;
    private String  captainName;
    private List<PrintItemDto> items;
    private double subtotal;
    private double discount;
    private double gst;
    private double total;
    private String paymentMode;

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Integer getTableId()               { return tableId; }
    public void    setTableId(Integer tableId){ this.tableId = tableId; }

    public String getTableName()                  { return tableName; }
    public void   setTableName(String tableName)  { this.tableName = tableName; }

    public String getCaptainName()                    { return captainName; }
    public void   setCaptainName(String captainName)  { this.captainName = captainName; }

    public List<PrintItemDto> getItems()                      { return items; }
    public void               setItems(List<PrintItemDto> items) { this.items = items; }

    public double getSubtotal()               { return subtotal; }
    public void   setSubtotal(double subtotal){ this.subtotal = subtotal; }

    public double getDiscount()               { return discount; }
    public void   setDiscount(double discount){ this.discount = discount; }

    public double getGst()          { return gst; }
    public void   setGst(double gst){ this.gst = gst; }

    public double getTotal()            { return total; }
    public void   setTotal(double total){ this.total = total; }

    public String getPaymentMode()                    { return paymentMode; }
    public void   setPaymentMode(String paymentMode)  { this.paymentMode = paymentMode; }
}