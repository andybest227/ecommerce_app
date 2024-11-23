package com.andy.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        return repository.save(mapper.toOrderLine(orderLineRequest)).getId();
    }

    public List<OrderLineResponse> findById(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::fromOrderLine)
                .collect(Collectors.toList());
    }
}
