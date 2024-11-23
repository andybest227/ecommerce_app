package com.andy.ecommerce.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer( CustomerRequest request) {
        if (request == null){
            return null;
        }
        return Customer.builder()
                .firstName(request.firstName())
                .id(request.id())
                .email(request.email())
                .lastName(request.lastName())
                .address(request.address())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress());
    }
}
