package com.example.mesh_test_task.service;

import com.example.mesh_test_task.domain.Admin;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminService {
    String getAdminToken(String login, String password);

    Admin getAdmin(String login, String password);

    UserDetails getUserByToken(String token);

    Admin save(Admin admin);
}
