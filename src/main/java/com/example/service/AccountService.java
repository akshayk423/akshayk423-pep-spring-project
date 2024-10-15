package com.example.service;

import javax.persistence.Access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.exception.InvalidCredentialException;
import com.example.exception.UnauthorizedLoginException;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account findAccountByUsername(String username){
        return accountRepository.findByUsername(username);
    }


    public Account saveAccount(Account account){
        if(accountRepository.findByUsername(account.getUsername()) != null)
            throw new UsernameAlreadyExistsException("Username is already registered");
        if(!account.getUsername().isBlank() && account.getPassword().length() > 4)
            return accountRepository.save(account);
        throw new InvalidCredentialException("Your account does not meet the requirements to register!");
    }


    public Account loginAccount(Account account){
        Account registered = findAccountByUsername(account.getUsername());
        if(registered == null)
            throw new UnauthorizedLoginException("Invalid Username");
        if(registered.getPassword().equals(account.getPassword()))
            return registered;
        throw new UnauthorizedLoginException("Invalid Password");
    }

}
