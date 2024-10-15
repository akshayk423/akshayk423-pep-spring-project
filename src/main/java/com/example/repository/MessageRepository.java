package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.entity.Message;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    public List<Message> findAll();

    public Optional<Message> findById(Integer messageId);

    public void deleteById(Integer messageId);

    public List<Message> findByPostedBy(Integer postedBy);


}
