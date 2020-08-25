package com.example.mesh_test_task.repository;

import com.example.mesh_test_task.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByLoginAndPassword(String login, String password);

    Admin findAdminByToken(String token);
}
