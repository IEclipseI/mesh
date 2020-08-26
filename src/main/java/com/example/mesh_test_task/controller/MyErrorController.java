package com.example.mesh_test_task.controller;

import com.example.mesh_test_task.controller.util.Msg;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Msg> handleError() {
        return new ResponseEntity<>(new Msg("Access denied"), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}