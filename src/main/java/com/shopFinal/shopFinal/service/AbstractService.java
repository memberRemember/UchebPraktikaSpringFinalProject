package com.shopFinal.shopFinal.service;

import java.util.List;
import java.util.UUID;

public interface AbstractService<T, UUID> {

    public List<T> findAll();

    public T createNote(T model);

    public T findById(UUID id);

    T updateNote(T model, UUID id);

    void deleteNote(UUID id);
}
