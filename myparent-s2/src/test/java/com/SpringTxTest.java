package com;

import com.tx.BookShopDao;
import com.tx.BookShopService;
import com.tx.CashierService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class SpringTxTest {
    private ClassPathXmlApplicationContext ac;
    private BookShopDao bookShopDao;
    private BookShopService bookShopService;
    private CashierService cashierService;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationTx.xml");
        bookShopDao =(BookShopDao)ac.getBean("bookShopDao");
        bookShopService =(BookShopService)ac.getBean("bookShopService");
        cashierService =(CashierService)ac.getBean("cashierService");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testBatchPurchase(){
        cashierService.batchPurchase(1,new int[]{1,2});
    }
    @Test
    public void testPurchase(){
        bookShopService.purchase(1,1);
    }
    @Test
    public void testBookShopDao(){
        System.out.println(bookShopDao.findBookPriceByBookId(1));
//        bookShopDao.updateBookStock(1);
//        bookShopDao.updateUserAccount(1,10);
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
