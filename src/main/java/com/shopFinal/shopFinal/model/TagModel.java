package com.shopFinal.shopFinal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tags")
public class TagModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Size(min = 3, message = "Имя не менее 3 символов")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @ManyToMany
    @JoinTable(name = "item_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<ItemModel> items;

    

    public TagModel() { }

    public TagModel(UUID id, @Size(min = 3, message = "Имя не менее 3 символов") String name, CategoryModel category,
            List<ItemModel> items) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.items = items;
    }

    @Override
    public String toString() {
        return "TagModel [id=" + id + ", name=" + name + ", categoryId=" + (category != null ? category.getId() : null) + "]";
    }
    

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }


}

