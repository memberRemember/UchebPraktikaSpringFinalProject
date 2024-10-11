package com.shopFinal.shopFinal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "items")
public class ItemModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Size(min = 3, message = "Имя не менее 3 символов")
    private String name;

    @Size(max = 255, message = "Описание не более 255 символов")
    private String description;

    // @DecimalMin(value = "1.00", message = "Минимальная цена не менее 1.00")
    // @DecimalMax(value = "99999.99", message = "Максимальная цена не более 99999.99")
    // private double price;

    private boolean isStattrak;

    @ManyToMany
    @JoinTable(name = "item_tag",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagModel> tags;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id")
    private TypeModel type;

    public ItemModel() {

    }

    public ItemModel(UUID id, @Size(min = 3, message = "Имя не менее 3 символов") String name,
            @Size(max = 255, message = "Описание не более 255 символов") String description,
            boolean isStattrak, List<TagModel> tags, TypeModel type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isStattrak = isStattrak;
        this.tags = tags;
        this.type = type;
    }


    @Override
    public String toString() {
        return "ItemModel [id=" + id + ", name=" + name + ", description=" + description + ", isStattrak=" + isStattrak
                + ", tags=" + tags + ", type=" + type + "]";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public double getPrice() {
    //     return price;
    // }

    // public void setPrice(double price) {
    //     this.price = price;
    // }

    public boolean isStattrak() {
        return isStattrak;
    }

    public void setStattrak(boolean isStattrak) {
        this.isStattrak = isStattrak;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    public TypeModel getType() {
        return type;
    }

    public void setType(TypeModel type) {
        this.type = type;
    }

    

    
}
