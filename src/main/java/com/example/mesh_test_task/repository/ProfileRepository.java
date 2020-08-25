package com.example.mesh_test_task.repository;

import com.example.mesh_test_task.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile getById(long id);

    Profile getByEmail(String email);

    boolean existsByEmail(String email);

    Profile findTopByOrderByCreatedDesc();
}
