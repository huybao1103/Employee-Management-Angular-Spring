package com.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {
    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String name;

    @Column(columnDefinition = "varchar(200)")
    private String address;

    @Column(columnDefinition = "varchar(200)")
    private String email;

    @OneToOne
    @JoinColumn(name = "department_head_emp_id")
    private Employee department_head;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

}
