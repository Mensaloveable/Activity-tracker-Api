package com.loveable.tracker.dto;

import com.loveable.tracker.enums.Status;
import com.loveable.tracker.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {
    private String title;
    private String description;
    private Status status;
    private Date createdAt;
    private String uuid;
    private User user;
}
