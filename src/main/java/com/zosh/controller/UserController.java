package com.zosh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.dto.UserDTO;
import com.zosh.exception.UserException;
import com.zosh.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfileHandler() throws UserException {
        // Fetch user profile based on currently authenticated user
        UserDTO userDTO = userService.findUserProfileByAuth();

        return new ResponseEntity<>(userDTO, HttpStatus.OK); // Return UserDTO in response
    }
}
