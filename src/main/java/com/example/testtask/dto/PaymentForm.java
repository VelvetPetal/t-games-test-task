package com.example.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentForm {

    private Integer productId;

    private ProductType productType;

    private PaymentMethod method;
    private String promoCode;
}
