package com.shopFinal.shopFinal.service;

import com.shopFinal.shopFinal.model.OrderModel;
import com.shopFinal.shopFinal.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InMemoryOrderService extends InMemoryAbstractService<OrderModel, UUID, OrderRepository>{
    private final OrderRepository repository;
    public InMemoryOrderService(OrderRepository jpaRepository, OrderRepository repository) {
        super(jpaRepository);
        this.repository = repository;
    }
}
