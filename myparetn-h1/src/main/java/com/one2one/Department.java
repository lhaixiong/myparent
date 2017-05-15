package com.one2one;


import javax.persistence.*;

@Entity
@Table(name = "h_department",schema = "lhxtest")
public class Department {
    private int departmentId;
    private String departmentName;
    private Manager manager;

    @Id
    @GeneratedValue
    @Column(name = "department_id")
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }


    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    @JoinColumn(name = "manager_id",unique = true)
    @OneToOne(targetEntity = Manager.class)
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
