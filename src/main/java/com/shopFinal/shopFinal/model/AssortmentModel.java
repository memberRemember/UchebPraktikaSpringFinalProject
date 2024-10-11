package com.shopFinal.shopFinal.model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "assortments")
public class AssortmentModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "item_tag_id")
    private ItemTagModel itemTag;

    @Min(1)
    @Max(100)
    private int quantity;

    @Min(1)
    private int price;

    @Nullable
    @ManyToMany
    @JoinTable(name = "order_assortment",
            joinColumns = @JoinColumn(name = "assortment_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<OrderModel> orders;

    public AssortmentModel() {
    }

    public AssortmentModel(UUID id, ItemTagModel itemTag, @Min(1) @Max(100) int quantity, @Min(1) int price,
            List<OrderModel> orders) {
        this.id = id;
        this.itemTag = itemTag;
        this.quantity = quantity;
        this.price = price;
        this.orders = orders;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ItemTagModel getItemTag() {
        return itemTag;
    }

    public void setItemTag(ItemTagModel itemTag) {
        this.itemTag = itemTag;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }
    
    
}
