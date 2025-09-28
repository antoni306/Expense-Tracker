package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // JSON: items -> pasuje do nazwy, OK
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore                  // nie chcemy aby front musiał podawać usera
    private User user;

    // JSON: totalCost -> mapujemy na transactionCost
    @JsonProperty("totalCost")
    private BigDecimal transactionCost;

    // JSON: date -> mapujemy na purchaseDate
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    // ----- helpers -----
    public void addItem(TransactionItem item){
        items.add(item);
    }

    public void setItems(List<TransactionItem> items){
        this.items.clear();
        if(items != null){
            for (TransactionItem i : items) addItem(i);
        }
    }

    // getters & setters
    public Long getId() { return id; }

    public List<TransactionItem> getItems() { return items; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public BigDecimal getTransactionCost() { return transactionCost; }
    public void setTransactionCost(BigDecimal transactionCost) { this.transactionCost = transactionCost; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
}
