package com.gradebook.entities.gradesInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GradesInfoRepository extends JpaRepository<GradesInfo, Integer> {
    @Query("Select g FROM GradesInfo g WHERE g.student_id=?1")
    Optional<GradesInfo[]> getUserGradesInfo(Integer id);
}
