package com;

import com.jdbc.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class SpringJDBCTest {
    private ClassPathXmlApplicationContext ac;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationJDBC.xml");
        jdbcTemplate=(JdbcTemplate)ac.getBean("jdbcTemplate");
        namedParameterJdbcTemplate=(NamedParameterJdbcTemplate)ac.getBean("namedParameterJdbcTemplate");
    }
    @After
    public void destroy(){

    }

    @Test
    public void testNameJdbcTemplate(){
        String sql="select order_id orderId,order_name orderName from h_order where order_id=:id";
        RowMapper rowMapper=new BeanPropertyRowMapper(Order.class);
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("id",50);
        Order order= (Order) namedParameterJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
        System.out.println(order);
    }
    /**
     * 查询统计
     */
    @Test
    public void testQueryForObject2(){
        String sql="select count(1) from h_news";
        long count=jdbcTemplate.queryForObject(sql,Long.class);
        System.out.println(count);
    }

    /**
     * 不要用jdbcTemplate.queryForList
     */
    @Test
    public void testQueryForList(){
        String sql="select order_id orderId,order_name orderName from h_order ";
        RowMapper rowMapper=new BeanPropertyRowMapper(Order.class);
        List<Order> list = jdbcTemplate.query(sql, rowMapper);
        System.out.println(list);
    }

    /**
     * 不支持级联，拿不了customer信息，轻量级jdbc，不是orm框架
     * 不要用jdbcTemplate.queryForObject(sql, Order.class,new Object[]{50});
     */
    @Test
    public void testQueryForObject(){
        String sql="select order_id orderId,order_name orderName,customer_id  as  \"customer.customerId\" from h_order where order_id=?";
        //org.springframework.jdbc.IncorrectResultSetColumnCountException: Incorrect column count: expected 1, actual 2
//        jdbcTemplate.queryForObject(sql, Order.class,new Object[]{50});
        RowMapper rowMapper=new BeanPropertyRowMapper(Order.class);
        Order order= (Order) jdbcTemplate.queryForObject(sql, rowMapper, 50);
        System.out.println(order);
    }

    @Test
    public void testBatchUpdate(){
        String sql="insert into h_news (id,author,title,public_date) values (employees_seq.nextval,?,?,?)";
        List<Object[]> batchArgs=new ArrayList<Object[]>();
        batchArgs.add(new Object[]{"AAA","AAA",new Date()});
        batchArgs.add(new Object[]{"BBB","BBB",new Date()});
        batchArgs.add(new Object[]{"CCC","CCC",new Date()});
        jdbcTemplate.batchUpdate(sql,batchArgs);
    }

    @Test
    public void testUpdate(){
        String sql="update h_news set author='yyyy' where id=?";
        jdbcTemplate.update(sql,new Object[]{2});
    }

    @Test
    public void testReference(){
        DataSource dataSource= (DataSource) ac.getBean("dataSource");
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
