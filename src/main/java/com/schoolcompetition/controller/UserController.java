package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.request.UserRequest.CreateUserRequest;
import com.schoolcompetition.model.dto.request.UserRequest.UpdateUserRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-management")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = {"login"})
    public ResponseEntity<ResponseObj> login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    @GetMapping(value = {"getList"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return userService.getListUsers(page, size);
    }
    @PostMapping("create")
    public ResponseEntity<ResponseObj> createUser(@RequestBody CreateUserRequest userRequest) {
        return userService.createUser(userRequest);
    }
    @PutMapping("update")
    public ResponseEntity<ResponseObj> updateUser(@RequestParam int id, @RequestBody UpdateUserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @PutMapping("delete")
    public ResponseEntity<ResponseObj> deleteUser(@RequestParam int id) {
        return userService.deleteUser(id);
    }
}
