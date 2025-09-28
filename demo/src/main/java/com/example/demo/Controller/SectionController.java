package com.example.demo.Controller;

import com.example.demo.Dto.TransactionCreateDto;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.User;
import com.example.demo.Services.MyUserDetailsService;
import com.example.demo.View.StatsCategoryView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SectionController {

    @Autowired
    private MyUserDetailsService userDetailsService;
//                                                        application/x-www-form-urlencoded
    @PostMapping("/transactions")
    public ResponseEntity<BigDecimal> createTransaction(@ModelAttribute TransactionCreateDto transaction,HttpSession session){
          String username = (String)session.getAttribute("username");
          try{
              userDetailsService.saveTransaction(transaction,username);

          }catch (Exception e){
              return ResponseEntity.badRequest().build();
          }
          var userBalance = userDetailsService.getUser(username).getBalance();
          return  ResponseEntity.ok(userBalance);
    }
    @PostMapping(value="/balance",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,BigDecimal> addToBalance(@RequestParam BigDecimal amount, HttpSession session){
        System.out.println("dodaje do balancu");

        var user=userDetailsService.getUser((String)session.getAttribute("username"));
        userDetailsService.addToBalance(user,amount);
        return Collections.singletonMap("balance",user.getBalance());
    }
    @GetMapping("/user-details")
    public ResponseEntity<Map<String,Object>> getUserDetails(HttpSession session){
        var user=userDetailsService.getUser((String)session.getAttribute("username"));

        var transactions=userDetailsService.getTransactions(user);
        var map =new HashMap<String,Object>();
        map.put("transactions",transactions);
        map.put("balance",user.getBalance());
        map.put("statsMonth",StatsCategoryView.getMoneyPerMonth(transactions));
        map.put("statsCategory",StatsCategoryView.getMoneyPerCategory(transactions));
        return ResponseEntity.ok(map);
    }
}
