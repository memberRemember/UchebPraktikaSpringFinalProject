package com.shopFinal.shopFinal.model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.List;
import java.util.UUID;


@Entity
public class UserModel {
    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String password;
    private double balance;
    private boolean deleted;

    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> roles;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderModel> orders;

    public UserModel() {}

    public UserModel(UUID id, String username, String password, double balance, boolean deleted,
            Set<RoleEnum> roles, java.util.List<OrderModel> orders) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.deleted = deleted;
        this.roles = roles;
        this.orders = orders;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    
    
}
