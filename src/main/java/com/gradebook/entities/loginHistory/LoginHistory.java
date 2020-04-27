package com.gradebook.entities.loginHistory;

import com.gradebook.entities.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "LoginHistory")
public class LoginHistory  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty()
    @Column(nullable = false)
    private Date sign_out_time;

    @NotEmpty()
    @Column(nullable = false)
    private Date sign_in_time;

    @ManyToOne
    private User user;
}
