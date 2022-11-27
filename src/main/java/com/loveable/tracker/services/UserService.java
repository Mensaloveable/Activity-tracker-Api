package com.loveable.tracker.services;

import com.loveable.tracker.dto.UserDto;
import com.loveable.tracker.utils.ApiResponse;

public interface UserService {
    ApiResponse userSignUp(UserDto userDto);

    ApiResponse userLogin(UserDto userDto);

    ApiResponse getUser(String uuid);

    ApiResponse getAllUsers();
}
