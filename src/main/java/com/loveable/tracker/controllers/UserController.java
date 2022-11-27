package com.loveable.tracker.controllers;

import com.loveable.tracker.dto.UserDto;
import com.loveable.tracker.services.UserService;
import com.loveable.tracker.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign_up")
    public ApiResponse userSignUp(@RequestBody UserDto userDto) {
        return userService.userSignUp(userDto);
    }

    @GetMapping("/login")
    public ApiResponse userLogin(@RequestBody UserDto userDto) {
        return userService.userLogin(userDto);
    }

    @GetMapping("/get_user")
    public ApiResponse getUser(@RequestParam("uuid") String uuid) {
        return userService.getUser(uuid);
    }

    @GetMapping("all_users")
    public ApiResponse getAllUsers() {
        return userService.getAllUsers();
    }
}
