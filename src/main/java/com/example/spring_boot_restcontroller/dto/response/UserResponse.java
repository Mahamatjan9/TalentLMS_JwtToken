package com.example.spring_boot_restcontroller.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private String id;
    private String email;
    private String firstName;
}
