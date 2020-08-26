package com.example.mesh_test_task.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfigurer implements WebMvcConfigurer {

    private final ProfileSetFilter profileSetFilter;

    public FilterConfigurer(ProfileSetFilter profileSetFilter) {
        this.profileSetFilter = profileSetFilter;
    }

    @Bean
    public FilterRegistrationBean<ProfileSetFilter> myFilterRegistrationBean() {
        FilterRegistrationBean<ProfileSetFilter> regBean = new FilterRegistrationBean<>();
        regBean.setFilter(profileSetFilter);
        regBean.addUrlPatterns("/profile/set");
        return regBean;
    }
}