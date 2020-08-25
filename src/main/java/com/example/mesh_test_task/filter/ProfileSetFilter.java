package com.example.mesh_test_task.filter;

import com.example.mesh_test_task.filter.util.MultiReadHttpServletRequest;
import com.example.mesh_test_task.form.ProfileInfo;
import com.example.mesh_test_task.service.ProfileService;
import com.google.gson.Gson;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebFilter(urlPatterns = "/profile/set")
@Order(2)
public class ProfileSetFilter extends GenericFilterBean {
    private final ProfileService profileService;

    public ProfileSetFilter(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MultiReadHttpServletRequest req = new MultiReadHttpServletRequest((HttpServletRequest) request);
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        try {
            ProfileInfo profileInfo = gson.fromJson(reader, ProfileInfo.class);
            if (profileService.emailAlreadyInUse(profileInfo.getEmail())) {
                HttpServletResponse res = (HttpServletResponse) response;
                PrintWriter writer = res.getWriter();
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                res.setStatus(403);
                writer.println(gson.toJson(Map.of("msg", "Email address is already in use")));
            } else {
                chain.doFilter(req, response);
            }
        } catch (RuntimeException e) {
            HttpServletResponse res = (HttpServletResponse) response;
            PrintWriter writer = res.getWriter();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(400);
            writer.println(gson.toJson(Map.of("msg", "Invalid request.")));
        }
    }
}
