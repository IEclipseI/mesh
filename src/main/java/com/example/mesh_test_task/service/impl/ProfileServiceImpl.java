package com.example.mesh_test_task.service.impl;

import com.example.mesh_test_task.domain.Profile;
import com.example.mesh_test_task.repository.ProfileRepository;
import com.example.mesh_test_task.service.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile addProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile findById(long id) {
        return profileRepository.getById(id);
    }

    @Override
    public Profile findByEmail(String email) {
        return profileRepository.getByEmail(email);
    }

    @Override
    public Profile findLastCreatedProfile() {
        return profileRepository.findTopByOrderByCreatedDesc();
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public boolean emailAlreadyInUse(String email) {
        return profileRepository.existsByEmail(email);
    }
}
