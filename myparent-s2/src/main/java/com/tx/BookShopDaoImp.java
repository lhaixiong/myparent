package com.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("bookShopDao")
public class BookShopDaoImp implements BookShopDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public int findBookPriceByBookId(int bookId) {
        String sql="select price from book where bookid=?";
        int price=jdbcTemplate.queryForObject(sql,Integer.class,bookId);
        System.out.println("booid="+bookId+"的价钱为"+price);
        return price;
    }

    @Override
    public void updateBookStock(int bookId) {
        String sql="update bookstock set stock=stock-1 where bookid=?";
        System.out.println("更新booid="+bookId+"的书本库存");
        jdbcTemplate.update(sql, bookId);
    }

    @Override
    public void updateUserAccount(int userId, int price) {
        String sql="update userinfo set balance=balance-?  where userid=?";
        System.out.println("更新用户id"+userId+"的账号余额");
        jdbcTemplate.update(sql,new Object[]{price,userId});
    }
}
