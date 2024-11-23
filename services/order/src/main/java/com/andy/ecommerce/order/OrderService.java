package com.andy.ecommerce.order;

import com.andy.ecommerce.customer.CustomerClient;
import com.andy.ecommerce.exception.BusinessException;
import com.andy.ecommerce.kafka.OrderConfirmation;
import com.andy.ecommerce.kafka.OrderProducer;
import com.andy.ecommerce.orderline.OrderLineRequest;
import com.andy.ecommerce.orderline.OrderLineService;
import com.andy.ecommerce.payment.PaymentClient;
import com.andy.ecommerce.payment.PaymentRequest;
import com.andy.ecommerce.product.ProductClient;
import com.andy.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderMapper mapper;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        //Check customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exist with  the provided ID"));

        //purchase the products --> product-ms (Template)
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        //persist order
        var order = repository.save(mapper.toOrder(request));


        //persist orderLine
        for (PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }


        // start payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        this.paymentClient.requestOrderPayment(paymentRequest);


        //send payment notification --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID:: %d", orderId)));
    }
}
