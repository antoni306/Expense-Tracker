package com.example.demo.View;


import java.math.BigDecimal;

public class TransactionItemView {
    private String category;
    private BigDecimal pricePerUnit;
    private BigDecimal quantity;

    public TransactionItemView(String category, BigDecimal quantity, BigDecimal pricePerUnit){
        this.category=category;
        this.quantity=quantity;
        this.pricePerUnit=pricePerUnit;
    }
    public String getCategory(){
        return category;
    }
    public BigDecimal getQuantity() {
        return quantity;
    }
    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }
}
