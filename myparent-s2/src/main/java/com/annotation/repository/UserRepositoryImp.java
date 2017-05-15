package com.annotation.repository;

import com.annotation.TestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository(value = "userRepository")
public class UserRepositoryImp implements UserRopository {
    @Autowired(required = false)
    @Qualifier(value = "testObject111")
    private TestObject testObject;
    @Override
    public void save() {
        System.out.println("UserRepositoryImp save");
        System.out.println(testObject);
    }
}
