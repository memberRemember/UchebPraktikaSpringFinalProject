package com.shopFinal.shopFinal.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "types")
public class TypeModel {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    public TypeModel() {
    }

    public TypeModel(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
    

    @Override
    public String toString() {
        return "TypeModel [id=" + id + ", name=" + name + "]";
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

    
}
