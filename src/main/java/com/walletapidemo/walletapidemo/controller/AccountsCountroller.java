package com.walletapidemo.walletapidemo.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletapidemo.walletapidemo.entity.Account;
import com.walletapidemo.walletapidemo.repository.IAccountsRepo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/api/v1")
@RestController

public class AccountsCountroller {
    @Autowired
    IAccountsRepo accountsRepo;
    @PostMapping("/accounts")
    
    public ResponseEntity<Account> save(@RequestBody Account account){
        try {
        return new ResponseEntity<>(accountsRepo.save(account), HttpStatus.CREATED);

        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(){
        try{
        List <Account> list= accountsRepo.findAll();
        if (list.isEmpty() || list.size()==0) {
            
        return new ResponseEntity<List<Account>> (HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Account>>  (list, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccounts(@PathVariable Long id){
        Optional<Account> accountt= accountsRepo.findById(id);     
        if (accountt.isPresent()) {
        return new ResponseEntity<Account>(accountt.get(), HttpStatus.OK);
        }
       return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);



}

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        try {
        return new ResponseEntity<>(accountsRepo.save(account), HttpStatus.CREATED);

        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable Long id){
        try {
        Optional<Account> accountt= accountsRepo.findById(id);  
        if (accountt.isPresent()) {
            accountsRepo.delete(accountt.get());
           
        }   
       return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);


        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
