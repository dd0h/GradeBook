package com.gradebook.entities.grades;

import com.gradebook.entities.users.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradesRepository extends JpaRepository<Grades, Integer> {
    @Query("Select g FROM Grades g WHERE g.user=?1")
    Optional<Grades[]> getAllUserGrades(User user);
}
