package com.lhx.jdk8.sundry;

public class FilterEmpsBySalory implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee employee) {
        return employee.getSalory()>4000;
    }
}
