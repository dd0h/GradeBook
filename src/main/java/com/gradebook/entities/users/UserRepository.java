package com.gradebook.entities.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT email FROM User")
    List<String> getAllEmails();

    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.status='STUDENT'")
    List<User> getAllStudents();

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
