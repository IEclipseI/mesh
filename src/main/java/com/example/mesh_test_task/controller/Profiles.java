package com.example.mesh_test_task.controller;

import com.example.mesh_test_task.controller.util.Msg;
import com.example.mesh_test_task.domain.Profile;
import com.example.mesh_test_task.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/profiles")
public class Profiles {
    private final ProfileService profileService;

    public Profiles(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(summary = "Lists all registered profiles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns produced id",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Profile.class))))
    })
    @GetMapping(path = "")
    public List<Profile> showProfiles() {
        return profileService.findAll();
    }

    @Operation(summary = "Get profile by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Profile.class))),
        @ApiResponse(responseCode = "404", description = "Profile with given id not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Msg.class)))
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getProfileById(@PathVariable Long id) {
        Profile profile = profileService.findById(id);
        if (profile == null) {
            return new ResponseEntity<>(new Msg("Profile with id: " + id + " not found."), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get profile by email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = Profile.class))),
        @ApiResponse(responseCode = "404", description = "Profile with given id not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Msg.class)))
    })
    @PostMapping(path = "/get", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProfileByEmail(@RequestBody Email emailReq) {
        Set<ConstraintViolation<Email>> violations = Validation.buildDefaultValidatorFactory()
            .getValidator()
            .validate(emailReq);
        if (!violations.isEmpty()) {
            ConstraintViolation<Email> next = violations.iterator().next();
            return new ResponseEntity<>(new Msg(next.getPropertyPath() + " " + next.getMessage()), HttpStatus.BAD_REQUEST);
        }
        String email = emailReq.email.toLowerCase();
        Profile profile = profileService.findByEmail(email);
        if (profile == null) {
            return new ResponseEntity<>(new Msg("Profile with email: " + email + " not found."), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get last created profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Profile.class)))
    })
    @GetMapping(path = "/last")
    public ResponseEntity<Object> getLastCreatedProfile() {
        Profile profile = profileService.findLastCreatedProfile();
        if (profile == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
    }

    @Data
    @NoArgsConstructor
    public static class Email {
        @NotBlank
        String email;
    }
}
