package com.app.exerciseapp.repository;

import com.app.exerciseapp.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(
        "SELECT p FROM Project p JOIN FETCH p.owner " +
        "WHERE (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
        "AND (:ownerId IS NULL OR p.owner.id = :ownerId)"
    )
    Page<Project> findAllProjectsWithFilter(String search, Long ownerId, Pageable pageable);
}
