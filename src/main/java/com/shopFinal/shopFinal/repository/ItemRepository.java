package com.shopFinal.shopFinal.repository;

import com.shopFinal.shopFinal.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemModel, UUID> {
}
