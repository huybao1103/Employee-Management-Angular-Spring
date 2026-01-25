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
}
