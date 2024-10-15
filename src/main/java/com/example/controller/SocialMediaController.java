package com.example.controller;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.*;
import com.example.exception.FailedMessageException;
import com.example.exception.InvalidCredentialException;
import com.example.exception.FailedMessageException;
import com.example.exception.MessageIdNotFoundException;
import com.example.exception.UnauthorizedLoginException;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @Controller
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
       
            Account newAccount = accountService.saveAccount(account);
            return ResponseEntity.ok(newAccount);

    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId){
        List<Message> messages = messageService.findbyAccountId(accountId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account login = accountService.loginAccount(account);
        return ResponseEntity.ok(login);

    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        Message message = messageService.findById(messageId);
        return ResponseEntity.ok(message);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> putMessage(@RequestBody Message newMessage){
        Message message = messageService.putMessage(newMessage);
        return ResponseEntity.ok(message);
    }

    
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody MessageUpdateRequest request){
        String messageText = request.getMessageText();
        if (messageText.isBlank() || messageText.length() > 255) {
            throw new FailedMessageException("Update invalid");
        }
        messageService.updateMessageById(messageId, messageText);
        return ResponseEntity.ok(1);
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){
        messageService.deleteMessageById(messageId);
        
        return ResponseEntity.ok(1);
    }

    @ExceptionHandler(MessageIdNotFoundException.class)
    public ResponseEntity<String> handleMessageIdNotFound(){
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameExists(UsernameAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(UnauthorizedLoginException.class)
    public ResponseEntity<String> handleUnathorizedLogin(UnauthorizedLoginException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(FailedMessageException.class)
    public ResponseEntity<String> handleFailedMessageUpdate(FailedMessageException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
