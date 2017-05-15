package com.lhx.jdk8.sundry;

public class FilterEmpsByAge implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee employee) {
        return employee.getAge()>35;
    }
}
