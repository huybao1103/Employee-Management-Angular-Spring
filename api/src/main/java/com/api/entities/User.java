package com.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(unique = true, nullable = false, columnDefinition = "varchar(20)")
    private String userName;

    @Column(nullable = false, columnDefinition = "varchar(500)")
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String role;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Column(nullable = true, columnDefinition = "varchar(500)")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
