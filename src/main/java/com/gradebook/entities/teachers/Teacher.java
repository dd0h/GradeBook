package com.gradebook.entities.teachers;

import com.gradebook.entities.grades.Grades;
import com.gradebook.entities.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "Teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @NotEmpty()
    @Column(nullable = false)
    private String subject;

    @OneToMany(mappedBy = "teacher")
    private Set<Grades> grades;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<Grades> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grades> grades) {
        this.grades = grades;
    }
}
