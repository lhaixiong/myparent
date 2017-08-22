import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.Group;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.service.GroupService;
import com.lhx.aggregate.service.UserService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springmvc.xml"})
public class AppTest {
    @Autowired
    private UserDao dao;
    @Autowired
    private UserService service;
    @Autowired
    private GroupService groupService;
    @Test
    public void testCRUD(){
//        User user=dao.getOne("from User where age=11");
//        System.out.println(user);

//        System.out.println(dao.excuteSql("update user set name='lhxx' where age=11"));
//        System.out.println(dao.getCurrentSession());
    }
    @Test
    public void test01(){
        groupService.initData();
    }
    @Test
    public void test03(){
        Session session = dao.getSessionFactory().openSession();
        String hql="from Group where type="+ AppConstant.GROUP_ADMIN;
        List<Group> groups = session.createQuery(hql).list();
        if (groups == null||groups.isEmpty()) {
            Group group=new Group();
            group.setName("admin");
            group.setCreater(1);
            group.setCreateTime(new Date());
            group.setType(AppConstant.GROUP_ADMIN);
            session.saveOrUpdate(group);
        }
        session.close();
    }
    @Test
    public void test02(){
    }

}
