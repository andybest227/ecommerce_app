package com.andy.ecommerce.payment;

import com.andy.ecommerce.customer.CustomerResponse;
import com.andy.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String reference,
        CustomerResponse customer
) {

}
