package com.gradebook.entities.users;

import com.gradebook.entities.grades.Grades;
import com.gradebook.entities.loginHistory.LoginHistory;
import com.gradebook.entities.teachers.Teacher;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty()
    @Size(max = 30)
    @Column(unique = true, nullable = false)
    private String username;

    @NotEmpty()
    @Size(max = 30)
    @Column(unique = true, nullable = true)
    private String email;

    @NotEmpty()
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @NotEmpty()
    @Size(max = 30)
    @Column(nullable = true, length = 30)
    private String surname;

    @NotEmpty()
    @Size(min = 6)
    @Column(nullable = false)
    private String password;

    @NotEmpty()
    @Size(max = 30)
    @Column(nullable = true, length = 30)
    private String status;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Teacher teacher;

    @OneToMany(mappedBy = "user")
    private Set<Grades> grades;

    @OneToMany(mappedBy = "user")
    private Set<LoginHistory> loginHistories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Grades> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grades> grades) {
        this.grades = grades;
    }

    public Set<LoginHistory> getLoginHistories() {
        return loginHistories;
    }

    public void setLoginHistories(Set<LoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
    }
}
