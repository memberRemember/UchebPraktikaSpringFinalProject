package com.shopFinal.shopFinal.service;

import com.shopFinal.shopFinal.model.TagModel;
import com.shopFinal.shopFinal.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InMemoryTagService extends InMemoryAbstractService<TagModel, UUID, TagRepository> {
    private final TagRepository repository;
    public InMemoryTagService(TagRepository jpaRepository, TagRepository repository) {
        super(jpaRepository);
        this.repository = repository;
    }
}
