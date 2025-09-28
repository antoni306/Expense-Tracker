package com.example.demo.View;

import java.awt.*;
import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsCategoryView {
    public static Map<String, BigDecimal> getMoneyPerCategory(List<TransactionView> transactions) {
        return transactions.stream()
                .flatMap(t->
                        t.getProducts().stream())
                .collect(Collectors.toMap(
                        TransactionItemView::getCategory,
                        item->item.getPricePerUnit().multiply(item.getQuantity()),
                        BigDecimal::add
                ));
    }
    public static Map<Month, BigDecimal> getMoneyPerMonth(List<TransactionView> transactions){
        var map=new HashMap<Month,BigDecimal>();
         transactions.forEach(t->{
                    var items=t.getProducts();
                    var month=t.getPurchaseDate().getMonth();
                    for (var item:items){
                        var value=item.getPricePerUnit().multiply(item.getQuantity());
                        var old=map.getOrDefault(month,BigDecimal.ZERO);
                        map.put(month,value.add(old));
                    }
                });
         return map;
    }
}
