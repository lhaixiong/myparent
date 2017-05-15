package com.lhxa.entity.account;


import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "sh_Account",schema = "lhxtest")
public class Account {
    private int accountId;
    private String name;
    private int age;
    private int balance;

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    @Range(min = 0L,message = "用户余额不足")
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                '}';
    }
}
