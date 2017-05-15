package com.dao;

import com.HibernateHelper;
import com.entity.News;
import org.hibernate.Session;

public class NewsDao {
    public void save(News news){
        Session session= HibernateHelper.getCurrentSession();
        System.out.println("session in NewsDao>>>"+session.hashCode());
//        session.save(news);
    }
}
