package com.example.demo.Repository;

import com.example.demo.Model.Transaction;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findAllByUser(User user);
}