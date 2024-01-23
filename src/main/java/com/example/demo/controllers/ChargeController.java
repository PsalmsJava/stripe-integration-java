package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Requests.ChargeRequest;
import com.example.demo.Requests.ChargeRequest.Currency;
import com.example.demo.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import lombok.extern.java.Log;

@Log
@Controller
public class ChargeController {

    @Autowired
    StripeService paymentService;

    @PostMapping
    public String charge(ChargeRequest chargeRequest, Model model) throws StripeException{
        chargeRequest.setDescription("Example Charge");
        chargeRequest.setCurrency(Currency.EUR);
        Charge charge = paymentService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status",charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex){
        model.addAttribute("Error", ex.getMessage());
        return "result";
    }
}
