package com.example.Sync.dto;

public class PrintItemDto {

    private Integer menuId;
    private String  name;
    private String  emoji;
    private double  price;
    private int     qty;

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Integer getMenuId()                { return menuId; }
    public void    setMenuId(Integer menuId)  { this.menuId = menuId; }

    public String getName()             { return name; }
    public void   setName(String name)  { this.name = name; }

    public String getEmoji()              { return emoji; }
    public void   setEmoji(String emoji)  { this.emoji = emoji; }

    public double getPrice()              { return price; }
    public void   setPrice(double price)  { this.price = price; }

    public int  getQty()          { return qty; }
    public void setQty(int qty)   { this.qty = qty; }
}