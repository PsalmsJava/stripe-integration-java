package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.Requests.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import jakarta.annotation.PostConstruct;

@Service
public class StripeService {
    @Value("${STRIPE_SECRET_KEY}")
    String secretKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = secretKey;
    }

    public Charge charge(ChargeRequest chargeRequest)throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{
        Map<String, Object> chargeParams;
        chargeParams = new HashMap<String,Object>();
        chargeParams.put("amount",chargeRequest.getAmount());
        chargeParams.put("currenct", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }
    
}
