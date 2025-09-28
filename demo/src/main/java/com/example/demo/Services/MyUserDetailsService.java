package com.example.demo.Services;

import com.example.demo.Dto.TransactionCreateDto;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.TransactionItem;
import com.example.demo.Repository.TransactionsRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.View.TransactionItemView;
import com.example.demo.View.TransactionView;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.example.demo.Model.User;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MyUserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;





    public User register(String username, String email, String rawPassword) {
        var user=userRepository.findByUserName(username);
        if(user!=null){
            return null;
        }
        user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(rawPassword);
        return userRepository.save(user);
    }
    public User getUser(String username){
        return userRepository.findByUserName(username);
    }
    public boolean usernameTaken(String username){
        return userRepository.findByUserName(username) != null;
    }
    public boolean emailTaken(String email){
        return userRepository.findByEmail(email) != null;

    }

    //zrobione nie sprawdzone
    public List<TransactionView> getTransactions(User user){
        var transactions=transactionsRepository.findAllByUser(user);
        var views= transactions.stream().map(t->{
            var items=t.getItems();
            var products=items.stream().map(i->new TransactionItemView(i.getCategory(),i.getQuantity(),i.getUnitPrice())).toList();
            return new TransactionView(t.getPurchaseDate(),t.getId(),products,t.getTransactionCost());
        }).toList();
        views.forEach(t-> System.out.println(t.getTransactionCost()));
        return views;
    }


    public void saveTransaction(TransactionCreateDto transactionDto, String username ) throws Exception {
        User user=userRepository.findByUserName(username);
        var transaction = new Transaction();
        transaction.setPurchaseDate(transactionDto.getPurchaseDate());
        transaction.setUser(user);
        for (int i = 0; i < transactionDto.getCategory().size(); i++) {
            var cat=transactionDto.getCategory().get(i);
            var quant=transactionDto.getQuantity().get(i);
            var price=transactionDto.getPrice().get(i);
            var item = new TransactionItem();
            item.setCategory(cat);
            item.setQuantity(new BigDecimal(quant));
            item.setUnitPrice(new BigDecimal(price));
            item.setLineTotal(new BigDecimal(quant*price));
            transaction.addItem(item);
        }
        transaction.setTransactionCost(transaction.getItems().stream().map(item->item.getQuantity().multiply(item.getUnitPrice())).reduce(BigDecimal::add).get());
        if(user.getBalance().intValue()-transaction.getTransactionCost().intValue()<0){
            throw new Exception("you don't have enough money");
        }else{
            System.out.println("transaction cost: "+transaction.getTransactionCost().intValue());
            System.out.println("balance: "+user.getBalance().intValue());
        }
        user.setBalance(user.getBalance().subtract(transaction.getTransactionCost()));
        var updated=userRepository.save(user);
        transaction.setUser(updated);
        for (var item:transaction.getItems()){
            item.setTransaction(transaction);
        }
        transactionsRepository.save(transaction);
    }
    public void addToBalance(User user, BigDecimal balance) {
        user.setBalance(user.getBalance().add(balance));
        userRepository.save(user);
    }
}
