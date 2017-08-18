import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springmvc.xml"})
public class AppTest {
    @Autowired
    private UserDao dao;
    @Autowired
    private UserService service;
    @Test
    public void testCRUD(){
//        User user=dao.getOne("from User where age=11");
//        System.out.println(user);

//        System.out.println(dao.excuteSql("update user set name='lhxx' where age=11"));
//        System.out.println(dao.getCurrentSession());
    }
    @Test
    public void test01(){
        service.testOne();
//        service.testTwo();
    }

}
