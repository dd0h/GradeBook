package com.gradebook.entities.grades;

import com.gradebook.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradesRepository extends JpaRepository<User, Integer> {
}
