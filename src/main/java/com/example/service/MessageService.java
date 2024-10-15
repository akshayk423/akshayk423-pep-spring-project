package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.Message;
import com.example.exception.FailedMessageException;
import com.example.exception.MessageIdNotFoundException;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AccountRepository accountRepository;

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }


    public void deleteMessageById(Integer id){
        
         if(messageRepository.findById(id).isPresent())
            messageRepository.deleteById(id);
          
        else
            throw new MessageIdNotFoundException("Message ID not found!");
    }

    public Message findById(Integer id){
       if(messageRepository.findById(id).isPresent())
        return messageRepository.getById(id);
       return null;
    }


    public void updateMessageById(Integer messageId, String messageText) {
        if(!messageRepository.findById(messageId).isPresent())
            throw new FailedMessageException("Update invalid");
        if(messageText.isBlank() || messageText.length() > 255)
            throw new FailedMessageException("Update invalid");
        else{
            Message message = messageRepository.getById(messageId);
            message.setMessageText(messageText);  
            messageRepository.save(message);
        }
       
    
    }


    public List<Message> findbyAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }


    public Message putMessage(Message newMessage) {
        if(newMessage.getMessageText().isBlank() || newMessage.getMessageText().length() > 255 || !accountRepository.findById(newMessage.getPostedBy()).isPresent()){
            throw new FailedMessageException("Message could not be created!");
        }
        return messageRepository.save(newMessage);
    }

}
