import com.lhxa.dao.account.AccountDao;
import com.lhxa.dao.book.BookDao;
import com.lhxa.dao.bookstock.BookStockDao;
import com.lhxa.service.comm.BookShopService;
import com.lhxa.service.comm.CashierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringHibernateTest {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookStockDao bookStockDao;
    @Autowired
    private BookShopService bookShopService;
    @Autowired
    private CashierService cashierService;
    @Test
    public void testService(){
//        bookShopService.purchase(1,1);
        cashierService.batchPurchase(1, new int[]{1,2});
    }
    @Test
    public void testDao(){
        System.out.println(bookDao.findBookPrice(1));
        bookStockDao.updateBookStock(1);
        System.out.println(accountDao.findById(1));

        //        Account account=accountDao.findById(1);
//        System.out.println(account);

//        Account account=new Account();
//        account.setName("aaa");
//        account.setAge(20);
//        account.setBalance(200);
//        accountDao.save(account);
    }
    @Test
    public void testDataSource(){
        try {
            System.out.println(dataSource.getConnection());
            System.out.println(dataSource.getConnection().getClass().getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
