package com.llr.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
public class MessageController {
    @GetMapping("/messages")
    public ResponseEntity getMessages() {
        return ResponseEntity.ok("This is a message.");
    }
}
