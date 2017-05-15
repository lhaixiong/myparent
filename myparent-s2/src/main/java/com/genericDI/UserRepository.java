package com.genericDI;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements BaseRepository<User> {
    @Override
    public void save() {
        System.out.println("UserRepository save");
    }
}
