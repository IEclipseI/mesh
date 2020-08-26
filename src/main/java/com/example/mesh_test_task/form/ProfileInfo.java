package com.example.mesh_test_task.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProfileInfo {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private long age;
}
