package com.genericDI;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {
    @Autowired
    private BaseRepository<T> baseRepository;
    public void save(){
        System.out.println("BaseService save");
        baseRepository.save();
        System.out.println(baseRepository);
    }
}
