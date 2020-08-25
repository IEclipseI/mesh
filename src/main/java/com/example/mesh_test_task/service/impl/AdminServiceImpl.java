package com.example.mesh_test_task.service.impl;

import com.example.mesh_test_task.domain.Admin;
import com.example.mesh_test_task.repository.AdminRepository;
import com.example.mesh_test_task.service.AdminService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public String getAdminToken(String login, String password) {
        Admin admin = adminRepository.findByLoginAndPassword(login, password);
        return admin == null ? null : admin.getToken();
    }

    @Override
    public Admin getAdmin(String login, String password) {
        return adminRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public UserDetails getUserByToken(String token) {
        Admin admin = adminRepository.findAdminByToken(token);
        if (admin == null)
            return null;
        return new User(admin.getLogin(), admin.getPassword(), AuthorityUtils.createAuthorityList("ADMIN"));
    }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
}
