package com.shopFinal.shopFinal.repository;

import com.shopFinal.shopFinal.model.CategoryModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
}
