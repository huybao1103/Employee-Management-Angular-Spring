package com.api.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department extends BaseEntity{
    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String address;

    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String email;

    @OneToOne
    @JoinColumn(name = "department_head_emp_id")
    private Employee department_head;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee getDepartment_head() {
        return department_head;
    }

    public void setDepartment_head(Employee department_head) {
        this.department_head = department_head;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
