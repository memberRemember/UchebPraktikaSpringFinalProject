package com.shopFinal.shopFinal.repository;

import com.shopFinal.shopFinal.model.ItemTagModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemTagRepository extends JpaRepository<ItemTagModel, UUID> {
}
