package com.lhxa.dao.book.imp;

import com.lhxa.dao.base.imp.BaseDaoImp;
import com.lhxa.dao.book.BookDao;
import com.lhxa.entity.book.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "bookDao")
public class BookDaoImp extends BaseDaoImp implements BookDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    @Override
    public int findBookPrice(int bookId) {
//        System.out.println("findBookPrice   session>>>"+getSession().hashCode());
//        System.out.println("findBookPrice   session>>>"+getSession().hashCode());
//        return 0;
        int price=getSession().get(Book.class, bookId).getPrice();
        System.out.println("bookId="+bookId+"的价钱为"+price);
        return price;
    }
}
