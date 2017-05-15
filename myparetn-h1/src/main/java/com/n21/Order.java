package com.n21;

import javax.persistence.*;

@Entity
@Table(name = "h_order",schema = "lhxtest")
public class Order {
    private int orderId;
    private String orderName;
    private Customer customer;

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "order_name")
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @JoinColumn(name = "customer_id")
//    @ManyToOne(targetEntity = Customer.class,fetch = FetchType.LAZY)
    @ManyToOne(targetEntity = Customer.class)//默认抓取策略fetch是FetchType.EAGER，发送左外连接查询customer
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", customer=" + customer +
                '}';
    }
}
