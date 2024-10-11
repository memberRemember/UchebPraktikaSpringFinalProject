package com.shopFinal.shopFinal.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class OrderModel {
    @Id
    @GeneratedValue
    private UUID id;
    private String date;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToMany
    @JoinTable(name = "order_assortment",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "assortment_id"))
    private List<AssortmentModel> assortments;

    public OrderModel() {}

    public OrderModel(UUID id, String date, double totalPrice, UserModel user, List<AssortmentModel> assortments) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.user = user;
        this.assortments = assortments;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<AssortmentModel> getAssortments() {
        return assortments;
    }

    public void setAssortments(List<AssortmentModel> assortments) {
        this.assortments = assortments;
    }

    public double calculateTotalPrice() {
        return assortments.stream()
                .mapToDouble(AssortmentModel::getPrice)
                .sum();
    }
}
