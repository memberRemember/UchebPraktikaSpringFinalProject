package com.shopFinal.shopFinal.repository;

import com.shopFinal.shopFinal.model.TagModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<TagModel, UUID> {
}
