package com.pranathi.taskmanager.controller;


import com.pranathi.taskmanager.dto.ApiResponse;
import com.pranathi.taskmanager.dto.UserCreateRequest;
import com.pranathi.taskmanager.entity.User;
import com.pranathi.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(
            @Valid @RequestBody UserCreateRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("User created successfully"));
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
