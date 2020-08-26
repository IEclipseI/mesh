package com.example.mesh_test_task.controller;

import com.example.mesh_test_task.controller.util.Msg;
import com.example.mesh_test_task.domain.Profile;
import com.example.mesh_test_task.form.ProfileInfo;
import com.example.mesh_test_task.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

@RestController
public class ProfileSet {
    private final ProfileService profileService;

    public ProfileSet(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(summary = "Adds new profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns produced id",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = IdUser.class))),
        @ApiResponse(responseCode = "400", description = "Invalid email",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Msg.class))),
        @ApiResponse(responseCode = "403", description = "Email is already in use",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Msg.class)))
    })
    @PostMapping(path = "/profile/set", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Object> profileSetPost(@RequestBody ProfileInfo profileInfo) throws RuntimeException {
        Set<ConstraintViolation<ProfileInfo>> violations = Validation.buildDefaultValidatorFactory()
            .getValidator()
            .validate(profileInfo);
        if (!violations.isEmpty()) {
            ConstraintViolation<ProfileInfo> next = violations.iterator().next();
            return new ResponseEntity<>(new Msg(next.getPropertyPath() + " " + next.getMessage()), HttpStatus.BAD_REQUEST);
        } else {
            String name = profileInfo.getName().toLowerCase();
            String email = profileInfo.getEmail().toLowerCase();
            long age = profileInfo.getAge();

            Profile profile = new Profile();
            profile.setName(name);
            profile.setEmail(email);
            profile.setAge(age);
            Profile savedProfile = profileService.addProfile(profile);
            return new ResponseEntity<>(new IdUser(savedProfile.getId()), HttpStatus.OK);
        }
    }

    @Value
    private static class IdUser {
        long idUser;
    }
}
