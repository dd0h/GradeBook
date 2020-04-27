package com.gradebook.entities.loginHistory;

import com.gradebook.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<User, Integer> {
}
