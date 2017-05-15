package com.lhxs3.converter;

import com.lhxs3.dao.DepartmentDao;
import com.lhxs3.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter implements Converter<String,Employee> {
    @Autowired
    private DepartmentDao departmentDao;
    @Override
    public Employee convert(String s) {
        if(s!=null){
//            ttt-ttt@qq.com-0-1001
//            格式：lastName-email-gender-department.id
            String[] vals=s.split("-");
            if(vals!=null&&vals.length==4){
                String lastName=vals[0];
                String email=vals[1];
                Integer gender= Integer.valueOf(vals[2]);
                Integer departmentId= Integer.valueOf(vals[3]);

                Employee employee=new Employee();
                employee.setLastName(lastName);
                employee.setEmail(email);
                employee.setGender(gender);
                employee.setDepartment(departmentDao.getDepartment(departmentId));

                System.out.println(s+"--------EmployeeConverter---------"+employee);
                return employee;
            }
        }
        return null;
    }
}
