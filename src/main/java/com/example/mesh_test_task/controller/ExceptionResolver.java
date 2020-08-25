package com.example.mesh_test_task.controller;

import com.example.mesh_test_task.controller.util.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Msg handleNoHandlerFound(NoHandlerFoundException e) {
        return new Msg("Access forbidden");
    }
}