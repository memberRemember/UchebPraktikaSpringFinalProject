package com.shopFinal.shopFinal.repository;

import com.shopFinal.shopFinal.model.AssortmentModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssortmentRepository extends JpaRepository<AssortmentModel, UUID> {
}
