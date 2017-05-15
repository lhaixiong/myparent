package com;

import com.dao.NewsDao;
import com.entity.News;
import com.n21.Customer;
import com.n21.Order;
import com.n2n.Category;
import com.n2n.Item;
import com.one2one.Department;
import com.one2one.Manager;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HibernateTest {
    private Session session;
    private Transaction transaction;
    @Before
    public void init(){
        session=HibernateHelper.getSession();
        transaction=session.getTransaction();
        transaction.begin();
    }
    @After
    public void destroy(){
        transaction.commit();
        session.close();
    }
    @Test
    public void testManageSession(){
        System.out.println("session member in HibernateTest>>>"+session.hashCode());
        Session session1= HibernateHelper.getCurrentSession();
        System.out.println("session1 in testManageSession>>>>"+session1.hashCode());
        Transaction transaction1=session1.getTransaction();
        transaction1.begin();
        News news=new News();
        news.setTitle("dddd");

        NewsDao newsDao=new NewsDao();
        newsDao.save(news);
        newsDao.save(news);
        newsDao.save(news);

        transaction1.commit();
        System.out.println(session1.isOpen());
    }
    @Test
    public void testSecondCache(){
        News news=session.get(News.class, 3);
        System.out.println(news.getTitle());

        session.getTransaction().commit();
        session.close();
        session=HibernateHelper.getSession();
        transaction=session.getTransaction();
        transaction.begin();

        News news2=session.get(News.class, 3);
        System.out.println(news2.getTitle());
    }
    @Test
    public void testNativeSQL(){
        String sql="select  * from h_customer";
        List list = session.createSQLQuery(sql).addEntity(Customer.class).list();
        for (Object o :list) {
            Customer customer= (Customer) o;
            System.out.println(customer);
        }
    }
    @Test
    public void testQBC(){
        Criteria criteria=session.createCriteria(Customer.class);
        criteria.add(Restrictions.ilike("customerName","c2"));
        List<Customer> list = criteria.list();
        for (Customer customer:list) {
            System.out.println(customer + ">>>size" + customer.getOrders().size());
        }
    }
    @Test
    public void testLeftJoinFetch(){
        String hql="select distinct c from Customer c left join fetch c.orders";
        List<Customer> list=session.createQuery(hql).list();
        for (Customer customer:list){
            System.out.println(customer+">>>size"+customer.getOrders().size());
//            if(customer.getOrders().size()>0){
//                for (Order order:customer.getOrders()){
//                    System.out.println(order);
//                }
//            }
        }
    }
    @Test
    public void testLeftJoin(){
        String hql="select distinct c from Customer c left join  c.orders";
        List<Customer> list=session.createQuery(hql).list();
        for (Customer customer:list){
            System.out.println(customer+">>>size"+customer.getOrders().size());
//            if(customer.getOrders().size()>0){
//                for (Order order:customer.getOrders()){
//                    System.out.println(order);
//                }
//            }
        }
    }
    @Test
    public void testHQL(){

    }
    @Test
    public void testn2nGet(){
        Item item=session.get(Item.class,38);
        for (Category category:item.getCategorySet()){
            System.out.println(category.getCategoyName());
        }
    }
    @Test
    public void testn2n(){
        Category c1=new Category();
        c1.setCategoyName("c1");

        Category c2=new Category();
        c2.setCategoyName("c2");

        Item i1=new Item();
        i1.setItemName("i1");

        Item i2=new Item();
        i2.setItemName("i2");

        c1.getItems().add(i1);
        c1.getItems().add(i2);
        c2.getItems().add(i1);
        c2.getItems().add(i2);

        //若没有设置其中一个表维护关系，有主键冲突
        i1.getCategorySet().add(c1);
        i1.getCategorySet().add(c2);
        i2.getCategorySet().add(c1);
        i2.getCategorySet().add(c2);


        session.save(c1);
        session.save(c2);
        session.save(i1);
        session.save(i2);
    }
    @Test
    public void test121Get(){
        Manager manager=session.get(Manager.class,24);
        System.out.println(manager.getDepartment().getDepartmentName());
    }
    @Test
    public void test121(){
        Manager manager=new Manager();
        manager.setManagerName("bb");

        Department department=new Department();
        department.setDepartmentName("bb");
        department.setManager(manager);

        session.save(manager);
        session.save(department);
    }
    @Test
    public void test12n(){
//        Customer customer=session.get(Customer.class,21);
//        Set<Order> orders=customer.getOrders();
//        Iterator<Order> iterator=orders.iterator();
//        while (iterator.hasNext()){
//            Order o=iterator.next();
//            System.out.println(o.getOrderName());
//        }
    }
    @Test
    public void testn21Delete(){
        Customer customer=session.get(Customer.class,21);
        session.delete(customer);
    }
    @Test
    public void testn21Update(){
        Order order=session.get(Order.class, 22);
        order.getCustomer().setCustomerName("aaa");
    }
    @Test
    public void testn21Get(){
        Order order=session.get(Order.class, 22);
        System.out.println(order.getOrderName());

        session.getTransaction().commit();
        session.close();
        session=HibernateHelper.getSession();
        transaction=session.getTransaction();
        session.getTransaction().begin();

        Customer customer=order.getCustomer();
        System.out.println(customer.getClass().getName());
        System.out.println(order.getOrderName()+">>>"+customer.getCustomerName());
    }
    @Test
    public void testn21Load(){
        Order order=session.load(Order.class,22);
        System.out.println(order.getOrderName());

        session.getTransaction().commit();
        session.close();
        session=HibernateHelper.getSession();
        transaction=session.getTransaction();
        session.getTransaction().begin();

        Customer customer=order.getCustomer();
        System.out.println(customer.getClass().getName());
        System.out.println(customer.getCustomerName());
    }
    @Test
    public void testn21Save(){
        Customer customer=new Customer();
        customer.setCustomerName("c2");
//        Customer customer=session.get(Customer.class,21);

        Order order1=new Order();
        order1.setOrderName("order1");
        order1.setCustomer(customer);

        Order order2=new Order();
        order2.setOrderName("order2");
        order2.setCustomer(customer);

        session.save(customer);
        session.save(order1);
        session.save(order2);

    }
    @Test
    public void testUpdate(){
        News news=session.get(News.class,3);
        news.setAuthor("lllxxn");
    }
    @Test
    public void testDoWork(){
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);
                //调用存储过程
            }
        });
    }
    @Test
    public void testLoad(){
        News news=session.load(News.class,2);
        System.out.println(news);
        System.out.println(news.getClass().getName());

//        session.close();//懒加载异常
//        System.out.println(news);
//        System.out.println(news.getClass().getName());

//        News news=session.load(News.class,1);//org.hibernate.ObjectNotFoundException: No row with the given identifier exists: [com.entity.News#1]
//        System.out.println(news);
//        System.out.println(news.getClass().getName());
    }
    @Test
    public void testGet(){
        News news=session.get(News.class,2);
        System.out.println(news);
        System.out.println(news.getClass().getName());
//        News news=session.get(News.class,1);//返回null
//        System.out.println(news);
//        System.out.println(news.getClass().getName());
    }
    @Test
    public void testSessionClear(){
//        News news=session.load(News.class,2);
//        session.clear();
//        News new1=session.load(News.class,2);
        News news=session.get(News.class, 2);
        session.clear();
        News new1=session.get(News.class, 2);
    }
    @Test
    public void testSessionRefresh(){
        News news=session.load(News.class,2);
        System.out.println(news);
        session.refresh(news);
        System.out.println(news);
    }
    @Test
    public void testSessionFlush2(){
        News news=new News();
        news.setAuthor("bb");
        news.setTitle("bb");
        news.setDate(new Date());
        session.save(news);
    }
    @Test
    public void testSessionFlush(){
        News news=session.load(News.class,2);
        news.setAuthor("uu");

        News news1= (News) session.createCriteria(News.class).uniqueResult();
        System.out.println(news1);
    }

    @Test
    public void testSessionCache(){
        News news1=session.load(News.class,2);
        News news2=session.load(News.class,2);
        System.out.println(news1==news2);
//        System.out.println(news1);
//        System.out.println(news2);
    }
    @Test
    public void testAddNews(){
        News news=new News();
        news.setAuthor("aa");
        news.setTitle("aa");
        news.setDate(new Date());
        session.save(news);
    }
}
