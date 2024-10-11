package com.shopFinal.shopFinal.repository;

import com.shopFinal.shopFinal.model.TypeModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypeRepository extends JpaRepository<TypeModel, UUID> {
}
