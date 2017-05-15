package com.lhxa.entity.book;

import javax.persistence.*;

@Entity
@Table(name = "sh_book",schema = "lhxtest")
public class Book {
    private int bookid;
    private String bookName;
    private int price;

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    @Column(name = "book_name")
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Basic
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
                '}';
    }
}
