package com.shopFinal.shopFinal.service;

import com.shopFinal.shopFinal.model.ItemTagModel;
import com.shopFinal.shopFinal.repository.ItemTagRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InMemoryItemTagService extends InMemoryAbstractService<ItemTagModel, UUID, ItemTagRepository> {
    private final ItemTagRepository repository;
    public InMemoryItemTagService(ItemTagRepository jpaRepository, ItemTagRepository repository) {
        super(jpaRepository);
        this.repository = repository;
    }
}
