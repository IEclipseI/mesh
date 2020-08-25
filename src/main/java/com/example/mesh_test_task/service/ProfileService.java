package com.example.mesh_test_task.service;

import com.example.mesh_test_task.domain.Profile;

import java.util.List;

public interface ProfileService {
    Profile addProfile(Profile profile);

    Profile findById(long id);

    Profile findByEmail(String email);

    Profile findLastCreatedProfile();

    List<Profile> findAll();

    boolean emailAlreadyInUse(String email);
}
