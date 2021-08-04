package com.example.paymeintegration.controller;

import com.example.paymeintegration.model.dto.ReqCardDto;
import com.example.paymeintegration.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payme")
public class Controller {
    @Autowired
    Services services;

    @RequestMapping(value = "/cards-create", method = RequestMethod.POST)
    ResponseEntity<?> createCard(@RequestParam String cardNumber,
                                 @RequestParam String expire) {
        return ResponseEntity.ok(services.createCard(new ReqCardDto(cardNumber, expire)));
    }
    @RequestMapping(value = "/cards-remove", method = RequestMethod.POST)
    ResponseEntity<?> removeCard(@RequestParam String token){
        return ResponseEntity.ok(services.removeCardByToken(token));
    }

}
