package com.lhx.aggregate.dao;

import com.lhx.aggregate.tools.PageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础dao接口
 * @param <T>
 */
public interface BaseDao<T> {
    /**
     * 保存一个对象
     * @param o
     * @return
     */
    Object save(T o);

    /**
     * 更新或保存一个对象
     * @param o
     * @return
     */
    void saveOrUpdate(T o);

    /**
     * 通过id删除一个对象
     * @param id
     */
    void deleteById(Serializable id);
    /**
     * 删除一个对象
     * @param o
     */
    void delete(T o);
    /**
     * 更新对象
     * @param o
     */
    void update(T o);

    /**
     * 获取一个对象
     * @param hql
     * @return
     */
    T getOne(String hql);

    /**
     * 通过条件获取一个对象
     * @param hql
     * @param params
     * @return
     */
    T getOne(String hql,Map<String,Object> params);

    /**
     * 查询多个对象
     * @param hql
     * @return
     */
    List<T> find(String hql);

    /**
     * 通过条件查询多个对象
     * @param hql
     * @param params
     * @return
     */
    List<T> find(String hql,Map<String,Object> params);

    /**
     * 查询所有对象
     * @return
     */
    List<T> findAll();

    /**
     * 分页查询
     * @param hql
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageBean<T> find(String hql,int pageNum,int pageSize);

    /**
     * 带条件的分页查询
     * @param hql
     * @param params
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageBean<T> find(String hql,Map<String,Object> params,int pageNum,int pageSize);

    /**
     * 总记录数
     * @return
     */
    long count();

    /**
     * 带条件的总记录数
     * @param hql
     * @param params
     * @return
     */
    long count(String hql,Map<String,Object> params);

    /**
     * 执行hql语句
     * @param hql
     * @return
     */
    int executeHql(String hql);

    /**
     * 执行sql语句
     * @param sql
     * @return
     */
    int excuteSql(String sql);

    T getById(Serializable id);
}
