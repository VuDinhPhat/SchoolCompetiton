package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = {"login"})
    public ResponseEntity<ResponseObj> login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ResponseObj> getAll() {
        return userService.getAll();
    }
}
