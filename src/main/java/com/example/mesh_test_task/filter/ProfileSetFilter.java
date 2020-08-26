package com.example.mesh_test_task.filter;

import com.example.mesh_test_task.filter.util.MultiReadHttpServletRequest;
import com.example.mesh_test_task.form.ProfileInfo;
import com.example.mesh_test_task.service.ProfileService;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@Component
public class ProfileSetFilter extends GenericFilterBean {
    private final ProfileService profileService;

    public ProfileSetFilter(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MultiReadHttpServletRequest req = new MultiReadHttpServletRequest((HttpServletRequest) request);
        BufferedReader reader = req.getReader();
        try {
            Gson gson = new Gson();
            ProfileInfo profileInfo = gson.fromJson(reader, ProfileInfo.class);
            if (profileService.emailAlreadyInUse(profileInfo.getEmail())) {
                writeResponse(response, 403, "Email address is already in use");
            } else {
                chain.doFilter(req, response);
            }
        } catch (RuntimeException e) {
            writeResponse(response, 400, "Invalid request.");
        }
    }

    @SneakyThrows
    private void writeResponse(ServletResponse response, int statusCode, String msg) {
        HttpServletResponse res = (HttpServletResponse) response;
        PrintWriter writer = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.setStatus(statusCode);
        writer.println(new Gson().toJson(Map.of("msg", msg)));
        writer.flush();
    }
}
