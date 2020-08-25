package com.example.mesh_test_task.controller;

import com.example.mesh_test_task.domain.ApiError;
import com.example.mesh_test_task.service.ApiErrorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Error {
    private final ApiErrorService apiErrorService;

    public Error(ApiErrorService apiErrorService) {
        this.apiErrorService = apiErrorService;
    }

    @Operation(summary = "Get last error")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(path = "/error/last")
    public ResponseEntity<Object> getLastCreatedProfile() {
        ApiError apiError = apiErrorService.findLastApiError();
        if (apiError == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(apiError, HttpStatus.OK);
        }
    }
}
