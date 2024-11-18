package com.app.exerciseapp.repository;

import com.app.exerciseapp.domain.User;
import com.app.exerciseapp.domain.enumeration.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u " + "WHERE u.role = :userRole")
    Page<User> findAllUsersWithFilter(UserRole userRole, Pageable pageable);
}
