package com.annotation.service;

import com.annotation.repository.UserRopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserService {
    @Autowired
    private UserRopository userRopository;
    public void save(){
        System.out.println("UserService save....");
        userRopository.save();
    }
}
