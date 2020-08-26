package com.example.mesh_test_task.controller;

import com.example.mesh_test_task.controller.util.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ExceptionController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Msg> messageNotReadable() {
        return new ResponseEntity<>(new Msg("Cannot parse request"), HttpStatus.BAD_REQUEST);
    }
}