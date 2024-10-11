package com.shopFinal.shopFinal.model;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class CategoryModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Size(min = 3, message = "Имя не менее 3 символов")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<TagModel> tags;

    public CategoryModel() {}

    public CategoryModel(UUID id, @Size(min = 3, message = "Имя не менее 3 символов") String name, Set<TagModel> tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }
    
    @Override
    public String toString() {
        return "CategoryModel [id=" + id + ", name=" + name + ", numberOfTags=" + (tags != null ? tags.size() : 0) + "]";
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

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
    }

    
}
