package com.gradebook.entities.grades;

import com.gradebook.entities.teachers.Teacher;
import com.gradebook.entities.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Grades")
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty()
    @Size(max = 1)
    @Column(nullable = false)
    private int grade;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    private Teacher teacher;
}
