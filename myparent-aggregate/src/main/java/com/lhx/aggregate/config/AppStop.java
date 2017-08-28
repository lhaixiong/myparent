package com.lhx.aggregate.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 应用关闭
 */
@Component
public class AppStop implements ApplicationListener<ContextClosedEvent>{
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("开始反注册drivers...");
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()){
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("反注册driver:"+driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
