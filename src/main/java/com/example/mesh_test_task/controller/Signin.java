package com.example.mesh_test_task.controller;

import com.example.mesh_test_task.domain.Admin;
import com.example.mesh_test_task.form.LoginForm;
import com.example.mesh_test_task.service.AdminService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Controller
public class Signin {
    private final static Random random = new SecureRandom();
    private final AdminService adminService;

    public Signin(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(path = "/signin")
    public String signinPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "signIn";
    }

    @PostMapping(path = "/signin")
    public String signinPage(Model model, LoginForm loginForm) {
        Admin admin = adminService.getAdmin(loginForm.getLogin(), sha1(loginForm.getPassword()));
        if (admin == null) {
            model.addAttribute("errorMessage", "Invalid login or password");
            return "signIn";
        }
        Instant tokenExpirationDate = admin.getTokenExpirationDate();
        if (tokenExpirationDate == null || tokenExpirationDate.isBefore(Instant.now())) {
            String token = randomSha512();
            Instant expires = Instant.now().plus(1, ChronoUnit.HOURS);
            admin.setToken(token);
            admin.setTokenExpirationDate(expires);
            Admin saved = adminService.save(admin);
            model.addAttribute("token", saved.getToken());
        } else {
            model.addAttribute("token", admin.getToken());
        }
        return "signIn";
    }

    private String sha1(String s) {
        return DigestUtils.sha1Hex(s);
    }

    private String randomSha512() {
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        return DigestUtils.sha3_512Hex(bytes);
    }
}
