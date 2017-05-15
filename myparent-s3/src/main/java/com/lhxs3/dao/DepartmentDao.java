package com.lhxs3.dao;

import com.lhxs3.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DepartmentDao {
    private static Map<Integer,Department> map=null;
    static {
        map=new HashMap<Integer, Department>();
        map.put(1001,new Department(1001,"D_AA"));
        map.put(1002,new Department(1002,"D_BB"));
        map.put(1003,new Department(1003,"D_CC"));
        map.put(1004,new Department(1004,"D_DD"));
        map.put(1005,new Department(1005,"D_EE"));
    }
    public Collection<Department> getDepartments(){
        return map.values();
    }
    public Department getDepartment(Integer id){
        return map.get(id);
    }
}
