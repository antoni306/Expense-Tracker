package com.example.demo.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionView {


    private final Long id;
    private final List<TransactionItemView> products;
    private final LocalDate purchaseDate;



    private BigDecimal transactionCost;
    public TransactionView(LocalDate purchaseDate,Long id,List<TransactionItemView> products,BigDecimal transactionCost){
        this.purchaseDate=purchaseDate;
        this.id=id;
        this.products=products;
        this.transactionCost=transactionCost;
    }
    public LocalDate getPurchaseDate(){
        return purchaseDate;
}
    public List<TransactionItemView> getProducts() {
        return products;
    }
    public BigDecimal getTransactionCost() {
        return transactionCost;
    }
    public Long getId() {
        return id;
    }
}
