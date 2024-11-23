package com.andy.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-lines")
public class OrderLineController {
    private final OrderLineService service;

    @GetMapping("/{order-id}")
    public ResponseEntity<List<OrderLineResponse>> findByOderId(
            @PathVariable("order-id")
            Integer orderId
    ){
        return ResponseEntity.ok(service.findById(orderId));
    }
}
