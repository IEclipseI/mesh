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
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Arrays;
import java.util.Set;

@RestController
@RequestMapping(path = "profile")
public class ProfileSet {
    private final ProfileService profileService;

    public ProfileSet(ProfileService profileService) {
        this.profileService = profileService;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public String handleException(Exception e) {
        e.printStackTrace();
        return Arrays.toString(e.getStackTrace());
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
    @PostMapping(path = "/set", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<IdUser> profileSetPost(@RequestBody ProfileInfo profileInfo) throws RuntimeException {
        String name = profileInfo.getName().toLowerCase();
        String email = profileInfo.getEmail().toLowerCase();
        long age = profileInfo.getAge();

        Profile profile = new Profile();
        profile.setName(name);
        profile.setEmail(email);
        profile.setAge(age);
        Set<ConstraintViolation<Profile>> violations = Validation.buildDefaultValidatorFactory()
            .getValidator()
            .validate(profile);
        if (!violations.isEmpty()) {
            throw new RuntimeException();
        } else {
            Profile savedProfile = profileService.addProfile(profile);
            return new ResponseEntity<>(new IdUser(savedProfile.getId()), HttpStatus.OK);
        }
    }

    @Value
    private static class IdUser {
        long idUser;
    }
}
