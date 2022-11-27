package com.loveable.tracker.serviceImpl;

import com.loveable.tracker.dto.UserDto;
import com.loveable.tracker.dto.UserResponseDto;
import com.loveable.tracker.model.User;
import com.loveable.tracker.repositories.UserRepository;
import com.loveable.tracker.services.UserService;
import com.loveable.tracker.utils.ApiResponse;
import com.loveable.tracker.utils.ResponseManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final ResponseManager<UserResponseDto> responseManager;
    private final ModelMapper mapper;

    @Override
    public ApiResponse userSignUp(UserDto userDto) {
        if (userDto.getFirstName() == null)
            return responseManager.error("First Name is required", BAD_REQUEST);
        else if (userDto.getLastName() == null)
            return responseManager.error("Last Name is required", BAD_REQUEST);
        else if (userDto.getGender() == null)
            return responseManager.error("Gender is required", BAD_REQUEST);
        else if (userDto.getEmail() == null)
            return responseManager.error("Email is required", BAD_REQUEST);
        else if (userDto.getPassword() == null)
            return responseManager.error("Password is required", BAD_REQUEST);

        boolean userExist = userRepo.existsByEmail(userDto.getEmail());
        if (userExist)
            return responseManager.error("Email already exist", BAD_REQUEST);

        User user = mapper.map(userDto, User.class);
        userRepo.save(user);
        UserResponseDto userResponseDto = mapper.map(user, UserResponseDto.class);
        return responseManager.success(userResponseDto, CREATED);
    }

    public ApiResponse<UserResponseDto> userLogin(UserDto userDto) {
        Optional<User> user = userRepo.findUsersByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        System.out.println("USER -> " + user);
        if (user.isPresent()) {
            UserResponseDto loggedInUser = mapper.map(user, UserResponseDto.class);
            System.out.println("LOGGED IN USER" + loggedInUser);
            return responseManager.success(loggedInUser, HttpStatus.FOUND);
        } else
            return responseManager.error("Incorrect credentials", CONFLICT);
    }

    @Override
    public ApiResponse getUser(String uuid) {
        Optional<User> user = userRepo.findByUuid(uuid);
        System.out.println(user);
        if (user.isPresent()) {
            UserResponseDto responseDto = mapper.map(user, UserResponseDto.class);
            return responseManager.success(responseDto, HttpStatus.FOUND);
        } else
            return responseManager.error("User with uuid: " + uuid + "does not exist", BAD_REQUEST);

    }

    @Override
    public ApiResponse getAllUsers() {
        ResponseManager<List<UserResponseDto>> rm = new ResponseManager<>();
        List<User> users = userRepo.findAll();
        List<UserResponseDto> responseDtoList = new ArrayList<>();
        if (users.isEmpty())
            return responseManager.error("No registered user", BAD_GATEWAY);
        users.forEach(user -> {
            UserResponseDto map = mapper.map(user, UserResponseDto.class);
            responseDtoList.add(map);
        });
        return rm.success(responseDtoList, FOUND);
    }


}
