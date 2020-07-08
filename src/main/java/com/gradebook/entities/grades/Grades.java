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
    @Column(nullable = false)
    private String grade;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    private Teacher teacher;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getGrade() { return grade; }

    public void setGrade(String grade) { this.grade = grade; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Teacher getTeacher() { return teacher; }

    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
}
