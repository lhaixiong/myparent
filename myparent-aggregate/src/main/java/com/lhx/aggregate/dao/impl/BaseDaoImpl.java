package com.lhx.aggregate.dao.impl;

import com.lhx.aggregate.dao.BaseDao;
import com.lhx.aggregate.tools.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class BaseDaoImpl<T> implements BaseDao<T> {
    protected Logger log;
    private Class<T> clz;
    @Autowired
    private SessionFactory sessionFactory;
    public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clz= (Class) type.getActualTypeArguments()[0];
        log=LoggerFactory.getLogger(this.getClass());
        System.out.println(this.getClass().getName()+"初始化....");
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Object save(T o) {
        return getCurrentSession().save(o);
    }

    @Override
    public void saveOrUpdate(T o) {
        getCurrentSession().saveOrUpdate(o);
    }

    @Override
    public void deleteById(Serializable id) {
        Object o = getCurrentSession().load(clz, id);
        getCurrentSession().delete(o);
    }

    @Override
    public void delete(T o) {
        getCurrentSession().delete(o);
    }

    @Override
    public void update(T o) {
        getCurrentSession().update(o);
    }

    @Override
    public T getOne(String hql) {
        Query query = getCurrentSession().createQuery(hql);
        List<T> list = query.list();
        if (list != null&&!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public T getOne(String hql, Map<String, Object> params) {
        Query query = getCurrentSession().createQuery(hql);
        if (params != null&&!params.isEmpty()) {
            for (String key : params.keySet()) {
                query.setParameter(key,params.get(key));
            }
        }
        List<T> list = query.list();
        if (list != null&&!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<T> find(String hql) {
        Query query = getCurrentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params) {
        Query query = getCurrentSession().createQuery(hql);
        if (params != null&&!params.isEmpty()) {
            for (String key : params.keySet()) {
                query.setParameter(key,params.get(key));
            }
        }
        return query.list();
    }

    @Override
    public List<T> findAll() {
        Query query = getCurrentSession().createQuery("from " + clz.getName());
        return query.list();
    }

    @Override
    public PageBean<T> find(String hql,String clz, int pageNum, int pageSize) {
        if(pageNum<1||pageSize<1){
            String err=String.format("分页参数错误{pageNum:%s,pageSize:%s}",pageNum,pageSize);
            log.error(err);
            throw new IllegalArgumentException(err);
        }
        Query query = getCurrentSession().createQuery(hql);
        List<T> result = query.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize).list();

        PageBean<T> pageBean=new PageBean<>();

        query=getCurrentSession().createQuery("select count(*) from "+clz);
        Object count = query.uniqueResult();

        pageBean.setResult(result);
        pageBean.setCurrentPage(pageNum);
        pageBean.setPageSize(pageSize);
        int allCount = Integer.parseInt(count.toString());
        pageBean.setPageAll(
                allCount % pageSize == 0 ? allCount / pageSize : (allCount / pageSize) + 1);
        pageBean.setCount(allCount);
        return pageBean;
    }

    @Override
    public PageBean<T> find(String hql,String clz, Map<String, Object> params, int pageNum, int pageSize) {
        if(pageNum<1||pageSize<1){
            String err=String.format("分页参数错误{pageNum:%s,pageSize:%s}",pageNum,pageSize);
            log.error(err);
            throw new IllegalArgumentException(err);
        }
        Query query = getCurrentSession().createQuery(hql);
        if (params != null&&!params.isEmpty()) {
            for (String key : params.keySet()) {
                query.setParameter(key,params.get(key));
            }
        }
        List<T> result = query.setFirstResult((pageNum - 1) * pageSize).setMaxResults(pageSize).list();

        PageBean<T> pageBean=new PageBean<>();
        query=getCurrentSession().createQuery("select count(*) from "+clz);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        Object count = query.uniqueResult();

        pageBean.setResult(result);
        pageBean.setCurrentPage(pageNum);
        pageBean.setPageSize(pageSize);
        int allCount = Integer.parseInt(count.toString());
        pageBean.setPageAll(
                allCount % pageSize == 0 ? allCount / pageSize : (allCount / pageSize) + 1);
        pageBean.setCount(allCount);
        return pageBean;
    }

    @Override
    public long count() {
        Query query=getCurrentSession().createQuery("select count(*) from " + clz.getSimpleName());
        return (long) query.uniqueResult();
    }

    @Override
    public long count(String hql, Map<String, Object> params) {
        Query query=getCurrentSession().createQuery("select count(*) from " + clz.getSimpleName());
        if (params != null&&!params.isEmpty()) {
            for (String key : params.keySet()) {
                query.setParameter(key,params.get(key));
            }
        }
        return (long) query.uniqueResult();
    }

    @Override
    public int executeHql(String hql) {
        return getCurrentSession().createQuery(hql).executeUpdate();
    }

    @Override
    public int excuteSql(String sql) {
        return getCurrentSession().createSQLQuery(sql).executeUpdate();
    }

    @Override
    public T getById(Serializable id) {
        return getCurrentSession().get(clz,id);
    }

}
