package com.api.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String user_name;

    @Column(nullable = false, columnDefinition = "varchar(500)")
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private Set<Employee> employees = new HashSet<>();

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
