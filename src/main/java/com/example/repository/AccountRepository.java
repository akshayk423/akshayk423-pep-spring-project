package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository <Account,Integer> {
    
    
    public Account findByUsername(String username);

    public Account save(Account newAccount);

    public Optional<Account> findById(Integer accountId);

}
