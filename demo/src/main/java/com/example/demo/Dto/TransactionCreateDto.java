package com.example.demo.Dto;

import java.time.LocalDate;
import java.util.List;

public class TransactionCreateDto {


    private  LocalDate purchaseDate;
    private  List<String> category;
    private  List<Double> price;
    private  List<Integer> quantity;
    public TransactionCreateDto(LocalDate date, List<String> category,List<Double> price,List<Integer> quantity){
        this.purchaseDate=date;
        this.category=category;
        this.price=price;
        this.quantity=quantity;
    }
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public List<String> getCategory() {
        return category;
    }

    public List<Double> getPrice() {
        return price;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void setPrice(List<Double> price) {
        this.price = price;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }
}
