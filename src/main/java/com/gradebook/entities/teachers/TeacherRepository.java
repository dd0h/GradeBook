package com.gradebook.entities.teachers;

import com.gradebook.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<User, Integer> {
}
