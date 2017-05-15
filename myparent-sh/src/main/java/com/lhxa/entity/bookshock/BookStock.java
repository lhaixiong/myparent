package com.lhxa.entity.bookshock;


import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "sh_bookstock",schema = "lhxtest")
public class BookStock {
    private int id;
    private int bookId;
    private int stock;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

//    @Range(min = 0L,message = "书本库存不足")
// 奇怪，该注解不生效，account的余额可以。。所以在数据库加了约束
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "BookShop{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", stock=" + stock +
                '}';
    }
}
