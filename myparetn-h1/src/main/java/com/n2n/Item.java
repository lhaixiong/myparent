package com.n2n;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "h_item",schema = "lhxtest")
public class Item {
    private int itemId;
    private String itemName;
    private Set<Category> categorySet=new HashSet<Category>();

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @JoinTable(name = "h_category_item",schema = "lhxtest",
            joinColumns = {@JoinColumn(name = "item_id",referencedColumnName = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id",referencedColumnName = "category_id")})
    @ManyToMany(targetEntity = Category.class)
    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }
}
