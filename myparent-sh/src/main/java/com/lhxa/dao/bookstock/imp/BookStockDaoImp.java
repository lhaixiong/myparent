package com.lhxa.dao.bookstock.imp;

import com.lhxa.dao.base.imp.BaseDaoImp;
import com.lhxa.dao.bookstock.BookStockDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "bookStockDao")
public class BookStockDaoImp extends BaseDaoImp implements BookStockDao{
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    @Override
    public void updateBookStock(int bookId) {
//        System.out.println("updateBookStock session>>>"+getSession().hashCode());
//        System.out.println("updateBookStock session>>>"+getSession().hashCode());

        System.out.println("更新bookId="+bookId+"的库存");
        String hql="update BookStock b set b.stock=b.stock-1 where b.bookId=?";
        getSession().createQuery(hql).setInteger(0,bookId).executeUpdate();
    }
}
