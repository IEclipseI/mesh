package com.example.mesh_test_task.controller;

import com.example.mesh_test_task.controller.util.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Default {
    @RequestMapping("/")
    public ResponseEntity<Msg> defaultMapping1() {
        return new ResponseEntity<>(new Msg("Access forbidden"), HttpStatus.FORBIDDEN);
    }
}
