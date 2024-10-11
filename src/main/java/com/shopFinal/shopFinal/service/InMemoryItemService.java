package com.shopFinal.shopFinal.service;

import com.shopFinal.shopFinal.model.ItemModel;
import com.shopFinal.shopFinal.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InMemoryItemService extends InMemoryAbstractService<ItemModel, UUID, ItemRepository> {
    private ItemRepository repository;
    public InMemoryItemService(ItemRepository jpaRepository) {
        super(jpaRepository);
        this.repository = jpaRepository;
    }
}
