package com.example.demo.Model;

//import com.example.demo.SecurityConfig;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 60)
    private String userName;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal balance=BigDecimal.ZERO;

    @Column(nullable = false,unique = true)
    private String email;



    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Transaction> transactions=new ArrayList<Transaction>();

    public List<Transaction> getTransactions() {
        return transactions;
    }


    public String getUsername() {
        return userName;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public Long getId() {
        return id;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    };
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
