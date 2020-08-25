package com.example.mesh_test_task.repository;

import com.example.mesh_test_task.domain.ApiError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiErrorRepository extends JpaRepository<ApiError, Long> {
    ApiError findTopByOrderByCreatedDesc();
}
