package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account createdAccount = accountService.registerAccount(account);

        if (createdAccount == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (createdAccount.getAccountId() != null && createdAccount.getAccountId() == -1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(createdAccount, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account);

        if (loggedInAccount != null) {
            return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(createdMessage, HttpStatus.OK);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id) {
        Message message = messageService.getMessageById(message_id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer message_id) {
        boolean isDeleted = messageService.deleteMessage(message_id);
        if (isDeleted) {
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer message_id, @RequestBody Message message) {
        boolean isUpdated = messageService.updateMessage(message_id, message.getMessageText());
        if (isUpdated) {
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer account_id) {
        List<Message> messages = messageService.getMessagesByAccountId(account_id);
        if (messages != null) {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
