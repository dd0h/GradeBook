package com.gradebook.entities.classes;

import com.gradebook.entities.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @Enumerated(EnumType.STRING)
    private ClassName className;

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

    public ClassName getClassName() { return className; }

    public void setClassName(ClassName className) { this.className = className; }
}
