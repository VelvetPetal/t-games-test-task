package com.example.testtask.controller;

import com.example.testtask.dto.PaymentForm;
import com.example.testtask.dto.ResultDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class PaymentController {
    @PostMapping("/payment/")
    private ResultDto handlePaymentForm(@RequestBody PaymentForm paymentForm) {
        return ResultDto.builder()
                .result(true)
                .paymenturl("https://bank-dai-deneg-bustro/951")
                .expirationTime(LocalDate.of(2023, 9, 9))
                .build();
    }
}
