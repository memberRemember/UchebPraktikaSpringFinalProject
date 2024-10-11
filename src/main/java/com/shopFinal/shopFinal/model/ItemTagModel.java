package com.shopFinal.shopFinal.model;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "item_tag")
public class ItemTagModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemModel item;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagModel tag;
    
    public ItemTagModel() {
    }

    public ItemTagModel(UUID id, ItemModel item, TagModel tag) {
        this.id = id;
        this.item = item;
        this.tag = tag;
    }


    
    @Override
    public String toString() {
        return "ItemTagModel [id=" + id + ", item=" + item + ", tag=" + tag + "]";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ItemModel getItem() {
        return item;
    }

    public void setItem(ItemModel item) {
        this.item = item;
    }

    public TagModel getTag() {
        return tag;
    }

    public void setTag(TagModel tag) {
        this.tag = tag;
    }

    
}
