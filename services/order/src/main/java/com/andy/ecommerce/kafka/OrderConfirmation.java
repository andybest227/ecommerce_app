package com.andy.ecommerce.kafka;

import com.andy.ecommerce.customer.CustomerResponse;
import com.andy.ecommerce.order.PaymentMethod;
import com.andy.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
        ) {
}
