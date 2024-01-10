package com.chris.data.dto.payment;

import lombok.Data;

@Data
public class CheckoutPayload {
    private String currency;
    private double amount;
    private String returnUrl;
    private String cancelUrl;
}
