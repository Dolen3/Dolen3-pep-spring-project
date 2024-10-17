package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            return null;
        }
        if (message.getMessageText().length() >= 255) {
            return null;
        }

        Account account = accountRepository.findById(message.getPostedBy()).orElse(null);
        if (account == null) {
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public boolean deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateMessage(Integer messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() >= 255) {
            return false;
        }
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return true;
        } else {
            return false;
        }
    }

    public List<Message> getMessagesByAccountId(Integer accountId) {
        if (accountRepository.existsById(accountId)) {
            return messageRepository.findByPostedBy(accountId);
        } else {
            return null;
        }
    }
}
