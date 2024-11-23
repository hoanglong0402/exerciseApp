package com.app.exerciseapp.repository;

import com.app.exerciseapp.domain.User;
import com.app.exerciseapp.domain.enumeration.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
        " SELECT u FROM User u " +
        "WHERE (:userRole is null or u.role = :userRole) " +
        "and (:userName IS NULL OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :userName, '%')))"
    )
    Page<User> findAllUsersWithFilter(UserRole userRole, String userName, Pageable pageable);
}
