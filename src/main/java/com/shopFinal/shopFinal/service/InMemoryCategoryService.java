package com.shopFinal.shopFinal.service;

import com.shopFinal.shopFinal.model.CategoryModel;
import com.shopFinal.shopFinal.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InMemoryCategoryService extends InMemoryAbstractService<CategoryModel, UUID, CategoryRepository> {
    private final CategoryRepository repository;
    public InMemoryCategoryService(CategoryRepository jpaRepository, CategoryRepository repository) {
        super(jpaRepository);
        this.repository = repository;
    }
}
