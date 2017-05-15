package com.lhxa.dao.account.imp;

import com.lhxa.dao.account.AccountDao;
import com.lhxa.dao.base.imp.BaseDaoImp;
import com.lhxa.entity.account.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "accountDao")
public class AccountDaoImp extends BaseDaoImp implements AccountDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Account findById(int accountId) {
        System.out.println("Account findById getSession>>"+getSession().hashCode());
        return getSession().get(Account.class, accountId);
    }

    @Override
    public void save(Account account) {
        getSession().save(account);
    }

    @Override
    public void updateAccount(int accountId, int price) {
//        System.out.println("更新用户账户余额 session>>"+getSession().hashCode());
//        System.out.println("更新用户账户余额 session2>>"+getSession().hashCode());
        System.out.println("更新用户账户余额");
        Account account=getSession().get(Account.class, accountId);
        account.setBalance(account.getBalance()-price);
        getSession().saveOrUpdate(account);
    }
}
