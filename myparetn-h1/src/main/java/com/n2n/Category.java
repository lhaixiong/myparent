package com.n2n;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "h_category",schema = "lhxtest")
public class Category {
    private int categroyId;
    private String categoyName;
    private Set<Item> items=new HashSet<Item>();

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    public int getCategroyId() {
        return categroyId;
    }

    public void setCategroyId(int categroyId) {
        this.categroyId = categroyId;
    }

    @Column(name = "category_name")
    public String getCategoyName() {
        return categoyName;
    }

    public void setCategoyName(String categoyName) {
        this.categoyName = categoyName;
    }

    //org.hibernate.AnnotationException: Associations marked as mappedBy must not define database mappings like @JoinTable or @JoinColumn: com.n2n.Category.items
//    @JoinTable(name = "h_category_item",schema = "lhxtest",
//    joinColumns = {@JoinColumn(name = "category_id",referencedColumnName = "category_id")},
//    inverseJoinColumns = {@JoinColumn(name = "item_id",referencedColumnName = "item_id")})
    @ManyToMany(targetEntity = Item.class,mappedBy ="categorySet")
    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
