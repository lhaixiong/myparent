package com.one2one;

import javax.persistence.*;

@Entity
@Table(name = "h_manager",schema = "lhxtest")
public class Manager {
    private int managerId;
    private String managerName;
    private Department department;

    @Id
    @GeneratedValue
    @Column(name = "manager_id")
    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Column(name = "manager_name")
    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @OneToOne(mappedBy = "manager")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
