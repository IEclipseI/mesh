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
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/profiles")
public class Profiles {
    private final ProfileService profileService;

    public Profiles(ProfileService profileService) {
        this.profileService = profileService;
    }

    @ExceptionHandler(value = Exception.class)
    public void handler(Exception e) {
        e.printStackTrace();
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
            return new ResponseEntity<>("Profile with id: " + id + " not found.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get profile by email")
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
    @PostMapping(path = "/get", consumes = "application/json")
    public ResponseEntity<Object> getProfileByEmail(@RequestBody Email emailReq) {
        String email = emailReq.email.toLowerCase();
        Profile profile = profileService.findByEmail(email);
        if (profile == null) {
            return new ResponseEntity<>("Profile with email: " + email + " not found.", HttpStatus.NOT_FOUND);
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

    @Value
    private static class Email {
        String email;
    }
}
