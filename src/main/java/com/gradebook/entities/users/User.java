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
    private String userName;

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
}
