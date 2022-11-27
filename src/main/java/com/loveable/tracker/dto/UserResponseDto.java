package com.loveable.tracker.dto;

import com.loveable.tracker.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String uuid;
}
