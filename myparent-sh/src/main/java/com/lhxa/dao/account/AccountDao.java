package com.lhxa.dao.account;

import com.lhxa.entity.account.Account;

public interface AccountDao {
    public Account findById(int accountId);
    public void save(Account account);

    /**
     * 更新用户账户余额
     * @param accountId
     * @param price
     */
    public void updateAccount(int accountId,int price);
}
