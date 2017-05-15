package com.lhxs3.dao;

import com.lhxs3.pojo.Department;
import com.lhxs3.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeDao {
    private static Map<Integer,Employee> map=null;
    @Autowired
    private  DepartmentDao departmentDao;
    static {
        map=new HashMap<Integer, Employee>();
        map.put(1001,new Employee(1001,"E-AA","AA@qq.com",0,new Department(1001,"D_AA")));
        map.put(1002,new Employee(1002,"E-BB","BB@qq.com",0,new Department(1002,"D_BB")));
        map.put(1003,new Employee(1003,"E-CC","CC@qq.com",0,new Department(1003,"D_CC")));
        map.put(1004,new Employee(1004,"E-DD","DD@qq.com",1,new Department(1004,"D_DD")));
        map.put(1005,new Employee(1005,"E-EE","EE@qq.com",1,new Department(1005,"D_EE")));
    }
    private static int empId=1006;
    public Collection<Employee> getAll(){
        return map.values();
    }
    public Employee getEmployee(Integer id){
        return map.get(id);
    }
    public void save(Employee employee){
        if(employee.getId()==null){
            employee.setId(empId);
            empId++;
        }
        employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
        map.put(employee.getId(),employee);
    }
    public void delete(Integer id){
        map.remove(id);
    }
}
