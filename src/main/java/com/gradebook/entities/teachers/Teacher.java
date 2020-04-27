package com.gradebook.entities.teachers;

import com.gradebook.entities.grades.Grades;
import com.gradebook.entities.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

}
