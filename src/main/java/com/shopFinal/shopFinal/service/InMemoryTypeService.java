package com.shopFinal.shopFinal.service;

import com.shopFinal.shopFinal.model.TypeModel;
import com.shopFinal.shopFinal.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InMemoryTypeService extends InMemoryAbstractService<TypeModel, UUID, TypeRepository> {
    private final TypeRepository repository;
    public InMemoryTypeService(TypeRepository jpaRepository, TypeRepository repository) {
        super(jpaRepository);
        this.repository = repository;
    }
}
