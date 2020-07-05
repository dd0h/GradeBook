package com.gradebook.entities.gradesInfo;

import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("Select g.id, g.grade, t.subject, u.name, u.surname, g.student_id" +
        " FROM Grades g INNER JOIN Users u on g.teacher_id=u.id INNER JOIN Teachers t on g.teacher_id=t.id")
public class GradesInfo {
    @Id
    private Integer id;
    private Integer grade;
    private String subject;
    private String name;
    private String surname;
    private Integer student_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrade() { return grade; }

    public void setGrade(Integer grade) { this.grade = grade; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }
}
